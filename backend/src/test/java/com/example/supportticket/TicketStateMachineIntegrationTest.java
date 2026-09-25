package com.example.supportticket;

import com.example.supportticket.model.TicketStatus;
import com.example.supportticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketStateMachineIntegrationTest {

 @Autowired
 MockMvc mvc;

 @Autowired
 TicketRepository repo;

 @BeforeEach
 void clean() {
  repo.deleteAll();
 }

 private long create() throws Exception {

  String response = mvc.perform(
                  post("/api/tickets")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content("""
                                {
                                  "title": "Login failure",
                                  "description": "Cannot login",
                                  "priority": "HIGH"
                                }
                                """)
          )
          .andExpect(status().isCreated())
          .andReturn()
          .getResponse()
          .getContentAsString();

  return new com.fasterxml.jackson.databind.ObjectMapper()
          .readTree(response)
          .get("id")
          .asLong();
 }

 private void move(long id, TicketStatus to, int expectedStatus)
         throws Exception {

  mvc.perform(
                  patch("/api/tickets/{id}/status", id)
                          .contentType(MediaType.APPLICATION_JSON)
                          .content("""
                                {
                                  "status": "%s"
                                }
                                """.formatted(to))
          )
          .andExpect(status().is(expectedStatus));
 }

 @Test
 void validPath() throws Exception {
  long id = create();

  move(id, TicketStatus.IN_PROGRESS, 200);
  move(id, TicketStatus.RESOLVED, 200);
  move(id, TicketStatus.CLOSED, 200);
 }

 @Test
 void cancelFromOpen() throws Exception {
  move(create(), TicketStatus.CANCELLED, 200);
 }

 @Test
 void cancelFromProgress() throws Exception {
  long id = create();

  move(id, TicketStatus.IN_PROGRESS, 200);
  move(id, TicketStatus.CANCELLED, 200);
 }

 @Test
 void closedToOpenRejected() throws Exception {
  long id = create();

  move(id, TicketStatus.IN_PROGRESS, 200);
  move(id, TicketStatus.RESOLVED, 200);
  move(id, TicketStatus.CLOSED, 200);
  move(id, TicketStatus.OPEN, 409);
 }

 @Test
 void resolvedToOpenRejected() throws Exception {
  long id = create();

  move(id, TicketStatus.IN_PROGRESS, 200);
  move(id, TicketStatus.RESOLVED, 200);
  move(id, TicketStatus.OPEN, 409);
 }

 @Test
 void cancelledToOpenRejected() throws Exception {
  long id = create();

  move(id, TicketStatus.CANCELLED, 200);
  move(id, TicketStatus.OPEN, 409);
 }

 @Test
 void openToClosedRejected() throws Exception {
  move(create(), TicketStatus.CLOSED, 409);
 }
}