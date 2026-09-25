package com.example.supportticket.service;
import com.example.supportticket.dto.*; import com.example.supportticket.exception.*; import com.example.supportticket.model.*; import com.example.supportticket.repository.TicketRepository; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service @Transactional public class TicketService {
 private static final Map<TicketStatus,Set<TicketStatus>> ALLOWED=Map.of(TicketStatus.OPEN,Set.of(TicketStatus.IN_PROGRESS,TicketStatus.CANCELLED),TicketStatus.IN_PROGRESS,Set.of(TicketStatus.RESOLVED,TicketStatus.CANCELLED),TicketStatus.RESOLVED,Set.of(TicketStatus.CLOSED),TicketStatus.CLOSED,Set.of(),TicketStatus.CANCELLED,Set.of());
 private final TicketRepository repo; public TicketService(TicketRepository repo){this.repo=repo;}
 public TicketDetailResponse create(CreateTicketRequest r){Ticket t=new Ticket();t.setTitle(r.title().trim());t.setDescription(r.description().trim());t.setPriority(r.priority());t.setAssignee(norm(r.assignee()));return detail(repo.save(t));}
 @Transactional(readOnly=true) public List<TicketSummaryResponse> list(String keyword,TicketStatus status){return repo.search(norm(keyword),status).stream().map(this::summary).toList();}
 @Transactional(readOnly=true) public TicketDetailResponse get(Long id){return detail(find(id));}
 public TicketDetailResponse update(Long id,UpdateTicketRequest r){Ticket t=find(id);t.setTitle(r.title().trim());t.setDescription(r.description().trim());t.setPriority(r.priority());t.setAssignee(norm(r.assignee()));return detail(t);}
 public TicketDetailResponse status(Long id,StatusUpdateRequest r){Ticket t=find(id);TicketStatus from=t.getStatus(),to=r.status();if(from==to)return detail(t);if(!ALLOWED.getOrDefault(from,Set.of()).contains(to))throw new InvalidStatusTransitionException("Invalid status transition: "+from+" -> "+to);t.setStatus(to);return detail(t);}
 public CommentResponse comment(Long id,CreateCommentRequest r){Ticket t=find(id);TicketComment c=new TicketComment();c.setTicket(t);c.setAuthor(r.author().trim());c.setBody(r.body().trim());t.getComments().add(c);repo.save(t);return new CommentResponse(c.getId(),c.getAuthor(),c.getBody(),c.getCreatedAt());}
 private Ticket find(Long id){return repo.findById(id).orElseThrow(()->new NotFoundException("Ticket not found: "+id));}
 private static String norm(String s){if(s==null)return null;String v=s.trim();return v.isBlank()?null:v;}
 private TicketSummaryResponse summary(Ticket t){return new TicketSummaryResponse(t.getId(),t.getTitle(),t.getPriority(),t.getStatus(),t.getAssignee(),t.getCreatedAt(),t.getUpdatedAt());}
 private TicketDetailResponse detail(Ticket t){return new TicketDetailResponse(t.getId(),t.getTitle(),t.getDescription(),t.getPriority(),t.getStatus(),t.getAssignee(),t.getCreatedAt(),t.getUpdatedAt(),t.getComments().stream().map(c->new CommentResponse(c.getId(),c.getAuthor(),c.getBody(),c.getCreatedAt())).toList());}
}
