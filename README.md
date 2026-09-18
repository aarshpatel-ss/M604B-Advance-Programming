# SocialConnect API

About: 'SocialConnect API' is the Spring Boot backend for a text-only social platform where users post their mood, questions, and opinions on topics they follow, comment on each other's posts, follow one another, and can report abusive accounts — reusing the domain from the Advance Database module's SocialConnect project, rebuilt here as a standalone REST API with an H2 embedded database (no external database server to install or connect to).

### Data source

The 20 seeded users (username, email, full name, country) are real data sourced from **[DummyJSON](https://dummyjson.com/users)**, a free REST API of realistic placeholder people, released under the **MIT License** ([github.com/Ovi/DummyJSON](https://github.com/Ovi/DummyJSON/blob/master/LICENSE)) — the same source cited in the Advance Database module project. Posts, comments, follows, reports, and topic-follows have no public-dataset equivalent at this scale, so those are hand-written.

**Suggested citation:** Owais, M. *DummyJSON — Free Fake REST API for Placeholder JSON Data*. https://dummyjson.com/ (MIT License, https://github.com/Ovi/DummyJSON).

### Running

Requires only JDK 17+ (Maven is bundled via the included wrapper):

```bash
./mvnw spring-boot:run
```

Then open `http://localhost:8080/` for the app, or `http://localhost:8080/swagger-ui.html` for the API docs.

## Input example

Taken from `FollowController` — a user follows another user, then a second call attempts to follow themselves:

```bash
curl -X POST localhost:8080/api/v1/follows -H "Content-Type: application/json" \
  -d '{"followerId": 2, "followeeId": 3}'

curl -X POST localhost:8080/api/v1/follows -H "Content-Type: application/json" \
  -d '{"followerId": 2, "followeeId": 2}'
```

Explanation: `FollowService.follow(...)` checks the follower and followee aren't the same person and that the pair doesn't already exist before saving — either check failing throws a custom exception instead of writing a bad row. A successful follow also logs the action into the activity log and dispatches a `FollowNotification`.

## Output example

Captured by running the two calls above against the seeded database:

```json
{"followerId":2,"followeeId":3,"followedAt":"2026-09-15"}
```
```json
{"timestamp":"2026-09-15T10:00:00","status":400,"error":"Bad Request","message":"A user cannot follow themselves","fieldErrors":null}
```

Explanation: the first call succeeds (HTTP 201) and is recorded both in the `Follows` table and, via `ActivityLogService`, in `User_Activity_Log`. The second is rejected before any row is written (HTTP 400) — not a database error, but the service layer's own business-rule check catching it and reporting plainly why, via the project's centralized exception handler.
