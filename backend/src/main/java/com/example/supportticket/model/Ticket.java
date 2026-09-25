package com.example.supportticket.model;
import jakarta.persistence.*; import java.time.Instant; import java.util.*;
@Entity @Table(name="tickets",indexes={@Index(name="idx_ticket_status",columnList="status"),@Index(name="idx_ticket_updated",columnList="updated_at")})
public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=160) private String title;
 @Column(nullable=false,length=5000) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private TicketPriority priority;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private TicketStatus status;
 @Column(length=160) private String assignee;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @OneToMany(mappedBy="ticket",cascade=CascadeType.ALL,orphanRemoval=true) @OrderBy("createdAt ASC") private List<TicketComment> comments=new ArrayList<>();
 @PrePersist void create(){Instant n=Instant.now();createdAt=n;updatedAt=n;if(status==null)status=TicketStatus.OPEN;}
 @PreUpdate void update(){updatedAt=Instant.now();}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public TicketPriority getPriority(){return priority;} public void setPriority(TicketPriority v){priority=v;} public TicketStatus getStatus(){return status;} public void setStatus(TicketStatus v){status=v;} public String getAssignee(){return assignee;} public void setAssignee(String v){assignee=v;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public List<TicketComment> getComments(){return comments;}
}
