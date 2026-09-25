# AI Review Log — Meaningful AI Mistakes

## 1. UI-only state enforcement
AI initially suggested disabling invalid transition buttons and relying on the frontend. Rejected because the requirement explicitly requires backend enforcement. Correction: `TicketService` contains an explicit transition map and REST integration tests call the backend.

## 2. Enum ordering for transitions
AI suggested allowing any status with a greater enum ordinal. Rejected because the state machine is a graph, not an ordering: OPEN->RESOLVED and OPEN->CLOSED are invalid. Correction: explicit allowed-transition sets.

## 3. Invalid transition as 400
AI suggested treating a validly-shaped but state-conflicting request as generic validation. Rejected. Correction: HTTP 409 Conflict with a specific transition message.

## 4. Browser localStorage as persistence
AI suggested localStorage to simplify the demo. Rejected because database persistence and restart survival are acceptance criteria. Correction: JPA with H2/PostgreSQL; frontend is API-only.

## Engineering rule
AI output is accepted only after comparison with `spec/*`, tests, acceptance criteria, and security/hygiene requirements.
