package com.tickets.api;

import com.tickets.api.application.TicketService;
import com.tickets.api.domain.Ticket;
import com.tickets.api.interfaces.TicketGraphQLController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TicketGraphQLControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private TicketService ticketService;


    @BeforeEach
    void setup() {
        lenient().when(ticketService.getAllTickets(0, 5))
                .thenReturn(Flux.just(new Ticket( "Usuario1", "Abierto")));
    }


    @Test
    void shouldGetAllTickets() {
        String query = """
            {
              "query": "query { getAllTickets(page: 0, size: 5) { id usuario status } }"
            }
            """;

        webTestClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(query)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.getAllTickets[0].id").isEqualTo(9)
                .jsonPath("$.data.getAllTickets[0].usuario").isEqualTo("Juan Perez")
                .jsonPath("$.data.getAllTickets[0].status").isEqualTo("ABIERTO");
    }
}
