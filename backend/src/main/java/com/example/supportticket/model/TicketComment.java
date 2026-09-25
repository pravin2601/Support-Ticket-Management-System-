package com.example.supportticket.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="ticket_comments") public class TicketComment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id",nullable=false) private Ticket ticket;
 @Column(nullable=false,length=160) private String author; @Column(nullable=false,length=3000) private String body;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt; @PrePersist void create(){createdAt=Instant.now();}
 public Long getId(){return id;} public void setTicket(Ticket v){ticket=v;} public void setAuthor(String v){author=v;} public String getAuthor(){return author;} public void setBody(String v){body=v;} public String getBody(){return body;} public Instant getCreatedAt(){return createdAt;}
}
