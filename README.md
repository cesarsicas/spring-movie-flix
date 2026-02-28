# SpringMovieFlix

A RESTful backend for a movie discovery and review platform. Users can browse movie releases, search titles, view details, and write reviews — all powered by the [WatchMode API](https://api.watchmode.com/) with a local PostgreSQL cache to reduce redundant external calls.

---

## Tech Stack

- **Java 17** + **Spring Boot 3**
- **Spring Security** — stateless JWT authentication
- **Spring Data JPA** + **PostgreSQL** — persistence
- **Flyway** — database migrations
- **Auth0 java-jwt** — JWT token generation and validation
- **Lombok** — boilerplate reduction
- **Gradle** — build tool

---

## Architecture

The project follows a layered architecture organized by feature modules:

```
src/main/java/.../modules/
├── user_auth/       # Login, signup, JWT issuance
├── user/            # User entity and management
├── default_user/    # User profiles (name, bio)
├── title/           # Movie data: releases, details, search
└── review/          # User reviews for titles
```

Each module is internally structured in four layers:

```
module/
├── api/             # Controllers and DTOs (HTTP boundary)
├── service/         # Business logic
├── data/            # Entities, repositories, mappers, remote clients
└── domain/          # Pure domain models (no framework dependencies)
```

### External API Integration

Movie data is fetched from the **WatchMode API**. To avoid hitting rate limits and reduce latency, responses are persisted locally in PostgreSQL the first time they are requested and served from the database on subsequent calls.

### Authentication & Authorization

Authentication is stateless and JWT-based. Each protected request must include a `Bearer` token in the `Authorization` header. There are two roles:

| Role | Description |
|---|---|
| `DEFAULT` | Regular user — can create a profile and write reviews |
| `ADMIN` | Administrative user |

---

## Database Schema

Managed by Flyway (migrations V1–V5):

| Table | Description |
|---|---|
| `users` | Accounts with login, hashed password, role, and soft-delete flag |
| `default_user` | User profile with name and bio |
| `reviews` | User reviews linked to a WatchMode title ID |
| `title_releases` | Cached release feed from WatchMode |
| `title_details` | Cached full title details (genres, ratings, cast info stored as JSON) |

---

## API Endpoints

### Auth — `/auth`

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/auth/signup` | Public | Register a new user |
| `POST` | `/auth/login` | Public | Login and receive a JWT token |

**Signup body:**
```json
{
  "login": "user@example.com",
  "password": "secret",
  "role": "DEFAULT"
}
```

**Login body:**
```json
{
  "login": "user@example.com",
  "password": "secret"
}
```

**Login response:**
```json
{
  "token": "<jwt>"
}
```

---

### Titles — `/titles`

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/titles/releases` | Public | List recent movie/show releases |
| `GET` | `/titles/search?query=` | Public | Search titles by name |
| `GET` | `/titles/{externalId}` | Public | Get full details for a title |
| `GET` | `/titles/{externalId}/reviews` | Public | List all reviews for a title |

> Title data is fetched from WatchMode on first request and cached locally in PostgreSQL.

**Example — search response item:**
```json
{
  "name": "Inception",
  "type": "movie",
  "id": 3173903,
  "year": 2010,
  "image_url": "https://..."
}
```

---

### Reviews — `/reviews`

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/reviews` | `DEFAULT` role | Submit a review for a title |

**Body:**
```json
{
  "externalTitleId": 3173903,
  "review": "A mind-bending masterpiece."
}
```

**Response:**
```json
{
  "id": 1,
  "externalTitleId": 3173903,
  "review": "A mind-bending masterpiece.",
  "defaultUserId": 2,
  "defaultUserName": "John"
}
```

> Requires a user profile to be created first (`POST /default/me`).

---

### User Profile — `/default`

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/default/me` | `DEFAULT` role | Get the authenticated user's profile |
| `POST` | `/default/me` | `DEFAULT` role | Create or update the user's profile |

**Body:**
```json
{
  "name": "John",
  "bio": "Movie enthusiast."
}
```

---

## Running Locally

### Prerequisites

- Java 17+
- PostgreSQL running on `localhost:5432`
- A [WatchMode API key](https://api.watchmode.com/)

### 1. Configure environment

Create `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot-movie-flix
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

api.security.token.secret=your_jwt_secret
api.watchmode.key=your_watchmode_api_key

server.port=8080
```

### 2. Create the database

```sql
CREATE DATABASE "springboot-movie-flix";
```

Flyway will apply all migrations automatically on startup.

### 3. Run

```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.
