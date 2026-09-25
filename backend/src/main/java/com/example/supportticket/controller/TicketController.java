package com.example.supportticket.controller;
import com.example.supportticket.dto.*; import com.example.supportticket.model.TicketStatus; import com.example.supportticket.service.TicketService; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/tickets") public class TicketController { private final TicketService service; public TicketController(TicketService s){service=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public TicketDetailResponse create(@Valid @RequestBody CreateTicketRequest r){return service.create(r);}
 @GetMapping public List<TicketSummaryResponse> list(@RequestParam(required=false) String keyword,@RequestParam(required=false) TicketStatus status){return service.list(keyword,status);}
 @GetMapping("/{id}") public TicketDetailResponse get(@PathVariable Long id){return service.get(id);}
 @PutMapping("/{id}") public TicketDetailResponse update(@PathVariable Long id,@Valid @RequestBody UpdateTicketRequest r){return service.update(id,r);}
 @PatchMapping("/{id}/status") public TicketDetailResponse status(@PathVariable Long id,@Valid @RequestBody StatusUpdateRequest r){return service.status(id,r);}
 @PostMapping("/{id}/comments") @ResponseStatus(HttpStatus.CREATED) public CommentResponse comment(@PathVariable Long id,@Valid @RequestBody CreateCommentRequest r){return service.comment(id,r);}
}
