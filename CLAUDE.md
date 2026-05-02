# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

---

## Commands

```bash
./gradlew bootRun                                                      # start dev server (port 8080)
./gradlew build                                                        # build JAR
./gradlew test                                                         # run all tests
./gradlew test --tests "FullyQualifiedClassName.methodName"            # run a single test
```

PostgreSQL must be running and `springboot-movie-flix` database must exist. Flyway runs migrations automatically on startup.

---

## Configuration

`src/main/resources/application.properties` (not committed — create locally):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot-movie-flix
spring.datasource.username=<db_user>
spring.datasource.password=<db_password>
api.security.token.secret=<jwt_secret>
api.watchmode.key=<watchmode_api_key>
api.agent.url=http://localhost:8081   # FastAPI AI agent (python-agent-flix)
video.base-path=<path_to_video_files>
live.stream.source=<path_to_mp4>
live.stream.output-dir=/tmp/hls-live
server.port=8080
```

---

## Architecture

### Module layout

All feature code lives under `modules/`. Each module has a consistent four-layer structure:

```
module/
├── api/        # @RestController + DTOs (HTTP boundary only)
├── service/    # Business logic (@Service)
├── data/       # @Entity, @Repository, mappers, remote API clients
└── domain/     # Plain Java models — no Spring/JPA annotations
```

Modules: `user_auth`, `user`, `default_user`, `title`, `review`, `transmission`, `chat`.  
Cross-cutting infrastructure (security, HLS streaming) lives in `infra/`.

### Auth / JWT flow

1. `SecurityFilter` (a `OncePerRequestFilter`) runs before every request. It reads the `Authorization: Bearer <token>` header, calls `TokenService.validateToken()` (Auth0 java-jwt), and sets a `UsernamePasswordAuthenticationToken` in the `SecurityContext`.
2. `SecurityConfigurations` wires the filter and declares which paths are public vs. role-gated. Adding a new protected endpoint means updating the `securityFilterChain` bean there.
3. Two roles: `DEFAULT` (users) and `ADMIN`. Fine-grained permissions (`DEFAULT_CREATE`, `ADMIN_READ`, `ADMIN_CREATE`) are defined in `RolePermissions` and granted via the `Role` enum.
4. JWT expiry is 2 hours. The `@AuthenticationPrincipal UserEntity` parameter on controller methods gives direct access to the resolved user.

### WatchMode caching pattern

`TitlesService` checks PostgreSQL first. On a cache miss it calls `WatchModeApiService` (a `RestTemplate` wrapper), persists the response, and returns it. The WatchMode API key is only consumed when data isn't already cached.

Cached tables: `title_releases`, `title_details`, `title_list_items`, `persons`, `title_sources`, `title_cast_crew`.

`getTitleDetails` always appends `append_to_response=sources,cast-crew` to the WatchMode request, so the `title_sources` and `title_cast_crew` child tables are populated on first fetch.

### WatchMode enums

- `SearchField` — typed enum for the `search_field` query param: `name`, `imdb_id`, `tmdb_movie_id`, `tmdb_tv_id`, `tmdb_person_id`.
- `AutocompleteFilterResultType` — typed enum for the autocomplete `search_type` param: `TITLES_AND_PEOPLE(1)`, `TITLES_ONLY(2)`, `MOVIES_ONLY(3)`, `TV_SHOWS_ONLY(4)`, `PEOPLE_ONLY(5)`.

### Chat SSE proxy

`POST /default/chat` (requires `DEFAULT` role) is a thin SSE proxy:
- `ChatController` extracts the authenticated user ID and delegates to `ChatService`.
- `ChatService` opens an `HttpClient` stream to `api.agent.url/chat` (the FastAPI python agent), reads `event:` / `data:` lines, and re-emits them as SSE through a `SseEmitter`.
- The frontend never talks to FastAPI directly — all AI chat goes through this endpoint.
- `session_id` sent to the agent equals `user.getId()`, so conversation history is per-user.

### HLS video streaming

`LiveStreamService` (in `infra/`) runs FFmpeg to segment an MP4 into HLS chunks written to `live.stream.output-dir`. `HlsWebMvcConfigurer` exposes that directory at `/live/**`. Title VOD streaming uses range-request support in `TitlesController`.

---

## Database migrations

Flyway migrations are in `src/main/resources/db/migration/`. Always add new schemas as a new `V<n>__description.sql` file — never edit existing ones.

| Migration | Table | Description |
|---|---|---|
| V1 | `users` | Auth accounts |
| V2 | `default_user` | User profiles |
| V3 | `reviews` | Title reviews |
| V4 | `title_releases` | Cached release feed |
| V5 | `title_details` | Cached full title details |
| V6 | `watch_party_movies` | Watch party movie list |
| V7 | `transmissions` | Live stream sessions |
| V8 | `title_list_items` | Cached list-titles results |
| V9 | `persons` | Cached person details |
| V10 | `title_sources` | Streaming source availability per title (FK → title_details) |
| V11 | `title_cast_crew` | Cast and crew per title (FK → title_details) |

---

## Title module endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/titles/releases` | Recent releases (optional `useCache`) |
| `GET` | `/titles/list` | Filtered title list (many optional params) |
| `GET` | `/titles/search` | Search by `search_value` + `searchField` enum (required), optional `types` |
| `GET` | `/titles/autocomplete-search` | Autocomplete by `query` + optional `filterResultType` enum |
| `GET` | `/titles/person/{personId}` | Person details (optional `useCache`) |
| `GET` | `/titles/{externalId}` | Full title details including sources and cast (optional `useCache`) |
| `GET` | `/titles/{externalId}/reviews` | Reviews for a title |
| `GET` | `/titles/{externalId}/stream` | VOD byte-range video stream |
