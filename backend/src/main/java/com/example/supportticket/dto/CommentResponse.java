package com.example.supportticket.dto; import java.time.Instant; public record CommentResponse(Long id,String author,String body,Instant createdAt){}
