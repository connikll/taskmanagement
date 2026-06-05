# Task Management REST API

## Description
Spring Boot application for task management with JWT authentication.

## Technologies
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- JWT
- H2 / PostgreSQL

## Endpoints
- POST /api/auth/register
- POST /api/auth/login
- POST /api/tasks
- GET /api/tasks
- GET /api/tasks/{id}
- PUT /api/tasks/{id}
- DELETE /api/tasks/{id}

## Running
mvn spring-boot:run