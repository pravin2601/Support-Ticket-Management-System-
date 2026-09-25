# Requirements

- Create, list, view, update tickets.
- Update title, description, priority, assignee.
- Add comments.
- Search title/description by keyword.
- Filter by status.
- Persist in database.
- Backend validation and meaningful UI errors.
- Backend enforces `OPEN -> IN_PROGRESS -> RESOLVED -> CLOSED`, with `OPEN -> CANCELLED` and `IN_PROGRESS -> CANCELLED`; all other transitions rejected.
- No secrets committed.
