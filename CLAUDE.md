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

`TitlesService` checks PostgreSQL first (`title_releases` / `title_details` tables). On a cache miss it calls `WatchModeApiService` (a simple `RestTemplate` wrapper), then persists the response before returning it. This means the WatchMode API key is only needed when data isn't already cached.

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

Flyway migrations are in `src/main/resources/db/migration/`. Current migrations V1–V7 create: `users`, `default_user`, `reviews`, `title_releases`, `title_details`, `watch_party_movies`, `transmissions`. Always add new schemas as a new `V<n>__description.sql` file — never edit existing ones.
