# Data Model

`Ticket(id,title,description,priority,status,assignee,createdAt,updatedAt)`

`TicketComment(id,ticket,author,body,createdAt)`

Status and priority are enums. Ticket has one-to-many comments with cascade/orphan removal.
