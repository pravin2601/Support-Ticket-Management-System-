package com.example.supportticket;

import com.example.supportticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketCrudIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired TicketRepository repo;

    @BeforeEach void clean() { repo.deleteAll(); }

    @Test
    void createUpdateSearchFilterAndCommentWork() throws Exception {
        String create = """
                {"title":"Payment API issue","description":"Payment API returns timeout","priority":"HIGH","assignee":"Alice"}
                """;
        String response = mvc.perform(post("/api/tickets").contentType(MediaType.APPLICATION_JSON).content(create))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("OPEN")).andReturn().getResponse().getContentAsString();
        long id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).get("id").asLong();

        mvc.perform(get("/api/tickets").param("keyword", "timeout"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));

        mvc.perform(get("/api/tickets").param("status", "OPEN"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));

        mvc.perform(put("/api/tickets/{id}", id).contentType(MediaType.APPLICATION_JSON).content(
                "{\"title\":\"Updated payment issue\",\"description\":\"Updated description\",\"priority\":\"CRITICAL\",\"assignee\":\"Bob\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Updated payment issue"))
                .andExpect(jsonPath("$.priority").value("CRITICAL")).andExpect(jsonPath("$.assignee").value("Bob"));

        mvc.perform(post("/api/tickets/{id}/comments", id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"Bob\",\"body\":\"Investigating the timeout.\"}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.author").value("Bob"));

        mvc.perform(get("/api/tickets/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].body").value("Investigating the timeout."));
    }
}
