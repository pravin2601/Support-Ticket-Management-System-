package com.example.supportticket.dto; import com.example.supportticket.model.TicketPriority; import jakarta.validation.constraints.*;
public record UpdateTicketRequest(@NotBlank @Size(max=160) String title,@NotBlank @Size(max=5000) String description,@NotNull TicketPriority priority,@Size(max=160) String assignee){}
