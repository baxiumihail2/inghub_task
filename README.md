# Store Management API

Backend-only store management API built with Java 17 and Spring Boot.

## Features

- Product management endpoints for adding, retrieving, listing, and changing product prices
- Basic HTTP authentication with role-based endpoint access
- Centralized error handling with structured JSON responses
- Service-layer logging for product lifecycle events
- Unit tests for the product service

## Stack

- Java 17+
- Maven
- Spring Boot 3
- Spring Security
- JUnit 5

## Project Structure

```text
src/main/java/com/example/store
  config/      security configuration
  error/       API exception handling
  product/     controllers, service, domain model
src/test/java/com/example/store
  product/     service tests
```

## Run

Use a Java 17+ JDK and Maven:

```bash
mvn spring-boot:run
```

## Test

```bash
mvn test
```

## Authentication

The API uses HTTP Basic authentication with two in-memory users:

- `admin` / `admin123` with role `ADMIN`
- `viewer` / `viewer123` with role `VIEWER`

## Authorization

- `POST /api/products`: `ADMIN`
- `PATCH /api/products/{id}/price`: `ADMIN`
- `GET /api/products`
- `GET /api/products/{id}`

The `GET` endpoints are accessible to both `ADMIN` and `VIEWER`.

## Example Requests

Create a product:

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Keyboard\",\"description\":\"Mechanical keyboard\",\"price\":89.99,\"quantity\":15}"
```

Change price:

```bash
curl -u admin:admin123 -X PATCH http://localhost:8080/api/products/{id}/price \
  -H "Content-Type: application/json" \
  -d "{\"price\":79.99}"
```

Fetch a product:

```bash
curl -u viewer:viewer123 http://localhost:8080/api/products/{id}
```

## Error Response Shape

```json
{
  "timestamp": "2026-03-30T10:15:30Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": [
    "price: Price must be greater than zero"
  ]
}
```

## Git Notes

This workspace did not expose a usable `git` binary or existing repository to the agent session, so repository initialization, commits, and push still need to be executed in an environment where Git is installed and configured.
