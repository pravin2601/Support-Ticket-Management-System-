# API Contract

- `POST /api/tickets` create (201)
- `GET /api/tickets?keyword=&status=` list/search/filter
- `GET /api/tickets/{id}` details
- `PUT /api/tickets/{id}` update editable fields
- `PATCH /api/tickets/{id}/status` status transition
- `POST /api/tickets/{id}/comments` add comment (201)

Validation errors are 400 with `fieldErrors`. Invalid state transitions are 409. Missing tickets are 404.
