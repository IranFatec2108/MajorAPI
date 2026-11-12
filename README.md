# Barbearia Control API

Portfolio backend project built with **Java** and **Spring Boot** for a real barbershop use case. The goal is to practice domain modeling, business rules, persistence, and REST API design while evolving the system incrementally.

## Objective

Build an API to support internal barbershop management, starting with **Services** and **Attendances** (core MVP) and evolving toward cash flow, basic inventory, subscribers, and financial indicators.

## Current status (MVP)

| Module | Status | Notes |
|---|---|---|
| **Services** | ✅ Complete (V1) | Create, list, find by ID, update, and deactivate (soft-delete via `active`). |
| **Attendances** | ✅ Complete (V1) | Register, list/filter, find by ID, partial update, deactivate/reactivate (soft-delete via `valid`). |
| **CORS** | ✅ Configured (dev) | Allows requests from `http://localhost:5173` for frontend integration. |
| **Database config** | ✅ Environment-based | PostgreSQL configured via environment variables; Hibernate `ddl-auto=none`. |
| **Advanced attendance queries** | 📋 Planned | Filters by date/service and pagination (pending decisions). |
| **Cash flow / indicators** | ❌ Not started | Planned after MVP stabilization. |
| **Inventory / subscribers / auth** | ❌ Not started | Future iterations. |

## V1 scope and progress

1. **Services** — ✅ done
2. **Attendances** — ✅ done
3. Attendance query enhancements (filters/pagination) — 📋 planned
4. Cash in/out — ❌ not started
5. Financial indicators — ❌ not started
6. Products and basic inventory — ❌ not started
7. Subscribers (if still needed) — ❌ not started

## Tech stack

- **Java:** 21
- **Spring Boot:** 4.1.2
- **Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL (via environment variables)
- **Validation:** Jakarta Bean Validation
- **Build:** Maven (`./mvnw`)

## Project structure

```text
src/main/java/br/com/iranfatec/majorapi/
├── attendance/   # Attendance entity, controller, manager, repository, DTOs, exceptions
├── service/      # ProvidedService entity, controller, manager, repository, DTOs, exceptions
├── exception/    # Global exception handling and API error response
└── config/       # CORS and other configuration
```

## API overview

### Services (`/services`)
- `POST /services` — Create a service (`201 Created`)
- `GET /services` — List services (`200 OK`)
- `GET /services/{id}` — Get service by ID (`200 OK` / `404`)
- `PUT /services/{id}` — Update a service (`200 OK`)
- `PATCH /services/{id}/deactivate` — Deactivate a service (`200 OK`)

**Rules:** unique name among active services, soft-delete with `active`, no reactivation in V1.

### Attendances (`/api/attendances`)
- `POST /api/attendances` — Register an attendance (`201 Created`)
- `GET /api/attendances?valid=true|false` — List attendances (defaults to valid), ordered by `dateTime DESC`
- `GET /api/attendances/{id}` — Get attendance by ID (`200 OK` / `404`)
- `PATCH /api/attendances/{id}` — Partially update an attendance (`200 OK`)
- `PATCH /api/attendances/{id}/deactivate` — Soft-deactivate (`valid=false`)
- `PATCH /api/attendances/{id}/reactivate` — Reactivate (`valid=true`)

**Rules:** only active services can be used for new attendances, `serviceNameSnapshot` preserves history, `amountPaid >= 0`, `dateTime` is set at creation.

## Error handling

Centralized via `GlobalExceptionHandler` (`@RestControllerAdvice`) with a standard `ApiErrorResponse(statusCode, message)`.

Typical statuses:
- `400 Bad Request` — validation errors
- `404 Not Found` — resource not found
- `409 Conflict` — business rule conflict (e.g. duplicate active service name)

## Configuration (environment variables)

The application loads environment variables from a local `.env` file when present (`spring.config.import=optional:file:.env[.properties]`) and can also receive them from the runtime environment (recommended for deployment).

Required variables:
- `DB_URL` — JDBC PostgreSQL URL (e.g. `jdbc:postgresql://localhost:5432/majorapi`)
- `DB_USER` — database username
- `DB_PASSWORD` — database password

> Do not commit `.env` or credentials to the repository. Configure environment variables in your deployment provider (e.g. Render) for production.

## How to run locally

1. **Prerequisites**
   - Java 21
   - Maven (or use the included `./mvnw`)
   - PostgreSQL running and a database created

2. **Configure environment variables** (example `.env` in project root, not committed):
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/majorapi
   DB_USER=your_user
   DB_PASSWORD=your_password
   ```

3. **Run the API**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Run tests**
   ```bash
   ./mvnw test
   ```

The API starts on `http://localhost:8080` by default.

## Frontend integration

A separate React (Vite + TypeScript) frontend consumes this API for the MVP (Services and Attendances CRUD flows). For local development, CORS is configured to allow `http://localhost:5173`. In production, update allowed origins to match the deployed frontend URL.

## Documentation

- [`docs/requisitos-servicos-v1.md`](docs/requisitos-servicos-v1.md) — Services module requirements and implementation notes
- [`docs/requisitos-attendance.md`](docs/requisitos-attendance.md) — Attendance module requirements and implementation notes
- [`docs/decisoes-em-aberto.md`](docs/decisoes-em-aberto.md) — Open product/business decisions

## Architecture

<img src="docs/img/arquitetura-servicos.png" width="400" alt="Services architecture diagram">

## Out of scope (current stage)

- Online scheduling
- WhatsApp automation
- Financial integrations/payments
- Mobile app
- Advanced dashboards/charts
- Authentication/authorization (planned for a future iteration)