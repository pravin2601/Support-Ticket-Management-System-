# Support Ticket Management System

Spec-driven Support Ticket Management System using Java 21, Spring Boot, H2/PostgreSQL, REST, and React/Vite.

## Workflow
Requirement → Specification → Plan / Tasks → Implementation → Testing → Review → Fix

## Structure
- `backend/` Spring Boot API
- `frontend/` React/Vite UI
- `spec/` approved specifications
- `.cursor/` reusable AI rules, commands, skills
- `.specstory/history/` prompt history
- `docs/` implementation/review evidence

## Run backend
```bash
cd backend
mvn spring-boot:run
```
Default H2 database is file-backed under `backend/data/`. API: `http://localhost:8080/api`.

PostgreSQL profile:
```bash
export SPRING_PROFILES_ACTIVE=postgres
export DB_URL=jdbc:postgresql://localhost:5432/support_ticket
export DB_USERNAME=support_ticket
export DB_PASSWORD=change-me
cd backend && mvn spring-boot:run
```
Or start PostgreSQL with `docker compose up -d postgres`.

## Run frontend
```bash
cd frontend
npm install
npm run dev
```
UI: `http://localhost:5173`.

## Test
```bash
cd backend
mvn test
```
The integration suite covers valid and invalid state transitions and validation.

## AI assessment evidence
This repository deliberately contains reusable Cursor instructions, specifications created before implementation, prompt history, review commands, and a log of meaningful AI mistakes that were caught and corrected.

No credentials or secrets are committed.
