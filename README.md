# SpringMovieFlix — Backend

A RESTful backend for a movie streaming and discovery platform. Users can browse releases, search titles, view full details with streaming sources and cast, write reviews, and chat with an AI movie assistant — all powered by the [WatchMode API](https://api.watchmode.com/) with a local PostgreSQL cache.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security — stateless JWT (Auth0 java-jwt) |
| Persistence | Spring Data JPA + PostgreSQL |
| Migrations | Flyway |
| Build | Gradle |
| Boilerplate | Lombok |

---
<img height="500" alt="image" src="https://github.com/user-attachments/assets/3d9e86f3-057b-479a-8dd8-c59262ee87b2" />


## Architecture

Feature code is organized into modules, each with four internal layers:

```
modules/
├── user_auth/       # Login, signup, JWT issuance
├── user/            # User entity and management
├── default_user/    # User profiles (name, bio)
├── title/           # Releases, details, search, autocomplete, person, streaming
├── review/          # User reviews linked to WatchMode title IDs
├── chat/            # SSE proxy to the Python AI agent
└── transmission/    # Live stream session management
```

```
module/
├── api/             # @RestController + DTOs  (HTTP boundary only)
├── service/         # Business logic
├── data/            # Entities, repositories, mappers, remote API clients
└── domain/          # Pure Java models — no Spring/JPA annotations
```

---

## Database Schema

Managed by Flyway (V1–V12):

| Table | Description |
|---|---|
| `users` | Auth accounts — login, hashed password, role, soft-delete |
| `default_user` | User profiles — name and bio |
| `reviews` | User reviews linked to a WatchMode title ID |
| `title_releases` | Cached WatchMode release feed |
| `title_details` | Cached full title metadata (genres, ratings, poster, trailer…) |
| `title_list_items` | Cached results from the WatchMode list-titles endpoint |
| `persons` | Cached person details from WatchMode |
| `title_sources` | Streaming source availability per title (Netflix, Amazon…) |
| `title_cast_crew` | Cast and crew per title |
| `genres` | Cached WatchMode genre list with TMDB IDs |
| `watch_party_movies` | Watch party movie queue |
| `transmissions` | Live HLS stream sessions |

`title_sources` and `title_cast_crew` are child tables of `title_details` (`ON DELETE CASCADE`). They are populated automatically when a title's details are first fetched.

---

## API Endpoints

### Auth — `/auth`

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/login` | Public | Login and receive a JWT |
| `POST` | `/auth/signup` | Public | Register a new user |
| `POST` | `/auth/admin-login` | Public | Login as admin |

**Login / Signup body:**
```json
{ "login": "user@example.com", "password": "secret", "role": "DEFAULT" }
```
**Login response:**
```json
{ "token": "<jwt>" }
```

---

### Titles — `/titles`

| Method | Path | Auth | Query params | Description |
|---|---|---|---|---|
| `GET` | `/titles/genres` | Public | `useCache` | Full genre list with WatchMode and TMDB IDs |
| `GET` | `/titles/releases` | Public | `useCache` | Recent releases |
| `GET` | `/titles/list` | Public | `types`, `genres`, `regions`, `source_ids`, `sort_by`, `page`, `limit`, … | Filtered title list |
| `GET` | `/titles/search` | Public | `search_value`*, `searchField`*, `types` | Search by name, IMDB ID, TMDB ID, or person |
| `GET` | `/titles/autocomplete-search` | Public | `query`*, `filterResultType` | Autocomplete suggestions |
| `GET` | `/titles/person/{personId}` | Public | `useCache` | Person details |
| `GET` | `/titles/{externalId}` | Public | `useCache` | Full title details (includes sources and cast) |
| `GET` | `/titles/{externalId}/reviews` | Public | — | Reviews for a title |
| `GET` | `/titles/{externalId}/stream` | Public | `Range` header | Byte-range VOD video stream |

`*` = required

**`searchField` values:** `name` · `imdb_id` · `tmdb_movie_id` · `tmdb_tv_id` · `tmdb_person_id`

**`filterResultType` values:** `TITLES_AND_PEOPLE` (default) · `TITLES_ONLY` · `MOVIES_ONLY` · `TV_SHOWS_ONLY` · `PEOPLE_ONLY`

**Example — genres (cached after first call):**
```bash
curl "http://localhost:8080/titles/genres"
# subsequent calls:
curl "http://localhost:8080/titles/genres?useCache=true"
```
```json
[
  { "id": 1, "externalId": 1, "name": "Action", "tmdb_id": 28 },
  { "id": 2, "externalId": 7, "name": "Drama",  "tmdb_id": 18 }
]
```

**Example — search by IMDB ID:**
```bash
curl "http://localhost:8080/titles/search?search_value=tt0944947&searchField=imdb_id"
```

**Example — autocomplete people only:**
```bash
curl "http://localhost:8080/titles/autocomplete-search?query=brian+cox&filterResultType=PEOPLE_ONLY"
```

**Title details response includes:**
```json
{
  "id": 3173903,
  "title": "Breaking Bad",
  "year": 2008,
  "genres": [7, 5, 17],
  "genre_names": ["Drama", "Crime", "Thriller"],
  "user_rating": 9.3,
  "sources": [
    { "source_id": 203, "name": "Netflix", "type": "sub", "region": "US", "web_url": "...", "format": "HD" }
  ],
  "cast": [
    { "person_id": 7121011, "type": "Cast", "full_name": "Bryan Cranston", "role": "Walter White", "episode_count": 62 }
  ]
}
```

---

### Reviews — `/reviews`

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/reviews` | `DEFAULT` | Submit a review for a title |

**Body:**
```json
{ "externalTitleId": 3173903, "review": "A masterpiece." }
```

> Requires a user profile (`POST /default/me`) to be created first.

---

### User Profile — `/default`

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/default/me` | `DEFAULT` | Get authenticated user's profile |
| `POST` | `/default/me` | `DEFAULT` | Create or update profile |
| `POST` | `/default/chat` | `DEFAULT` | Stream AI chat response (SSE) |

**Profile body:**
```json
{ "name": "John", "bio": "Movie enthusiast." }
```

**Chat body:**
```json
{ "message": "Recommend me a thriller like Inception" }
```
> Chat responses are streamed as `text/event-stream`. The AI service (`python-agent-flix`) maintains per-user conversation history via Redis.

---

### Transmissions — `/transmissions`

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/transmissions/movies` | Public | List movies available for live stream |
| `GET` | `/transmissions/current` | Public | Get current live stream info |
| `POST` | `/transmissions/start` | `ADMIN` | Start a live HLS stream |

---

## Running Locally

### Prerequisites

- Java 17+
- PostgreSQL on `localhost:5432`
- [WatchMode API key](https://api.watchmode.com/)

### 1. Create the database

```sql
CREATE DATABASE "springboot-movie-flix";
```

### 2. Configure environment

Create `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot-movie-flix
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

api.security.token.secret=your_jwt_secret
api.watchmode.key=your_watchmode_api_key
api.agent.url=http://localhost:8081

video.base-path=/path/to/videos
live.stream.source=/path/to/source.mp4
live.stream.output-dir=/tmp/hls-live

server.port=8080
```

### 3. Run

```bash
./gradlew bootRun
```

Flyway applies all migrations automatically on startup. The API is available at `http://localhost:8080`.

---

## Related Services

| Service | Port | Repo path |
|---|---|---|
| Spring backend (this) | 8080 | `backend/` |
| React frontend | 5173 | `react-movie-flix/` |
| Python AI agent | 8081 | `python-agent-flix/` |
| Redis (chat memory) | 6379 | — |
