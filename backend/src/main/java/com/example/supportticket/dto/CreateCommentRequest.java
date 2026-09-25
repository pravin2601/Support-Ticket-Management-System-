package com.example.supportticket.dto; import jakarta.validation.constraints.*; public record CreateCommentRequest(@NotBlank @Size(max=160) String author,@NotBlank @Size(max=3000) String body){}
