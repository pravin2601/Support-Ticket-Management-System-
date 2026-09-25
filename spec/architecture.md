# Architecture

Backend: Controller -> Service -> Repository -> H2/PostgreSQL. DTOs isolate the API from entities. Service owns state-machine business rules. React/Vite is a thin client; the backend remains the source of truth.
