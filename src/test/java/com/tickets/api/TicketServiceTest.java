package com.tickets.api;

import com.tickets.api.application.TicketService;
import com.tickets.api.domain.TickerRepository;
import com.tickets.api.domain.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TickerRepository tickerRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldGetAllTickets() {
        int page = 0;
        int size = 5;
        int offset = page * size;

        Ticket ticket1 = new Ticket("Ticket 1", "Abierto");
        Ticket ticket2 = new Ticket("Ticket 2", "Cerrado");
        when(tickerRepository.findAllWithPagination(size, offset))
                .thenReturn(Flux.just(ticket1, ticket2));

        StepVerifier.create(ticketService.getAllTickets(page, size))
                .expectNext(ticket1)
                .expectNext(ticket2)
                .verifyComplete();

        verify(tickerRepository, times(1)).findAllWithPagination(size, offset);
    }

    @Test
    void shouldGetTicketById() {
        Ticket ticket = new Ticket("Ticket de jasson", "abierto");
        when(tickerRepository.findById(1L)).thenReturn(Mono.just(ticket));

        StepVerifier.create(ticketService.getTicketById(1L))
                .expectNext(ticket)
                .verifyComplete();
    }

    @Test
    void shouldCreateTicket() {
        Ticket ticket = new Ticket("Ticket Test", "Abierto");
        Ticket savedTicket = new Ticket("Usuario test", "Abierto");

        when(tickerRepository.save(any(Ticket.class))).thenReturn(Mono.just(savedTicket));

        StepVerifier.create(ticketService.createTicket(ticket))
                .expectNext(savedTicket)
                .verifyComplete();
    }

    @Test
    void shouldUpdateTicket() {
        Ticket existingTicket = new Ticket("Ticket Test", "Abierto");
        Ticket updatedTicket = new Ticket("Ticket user test", "Abierto");

        when(tickerRepository.findById(1L)).thenReturn(Mono.just(existingTicket));
        when(tickerRepository.save(any(Ticket.class))).thenReturn(Mono.just(updatedTicket));

        StepVerifier.create(ticketService.updateTicket(1L, updatedTicket))
                .expectNext(updatedTicket)
                .verifyComplete();
    }

    @Test
    void shouldDeleteTicket() {
        Long ticketId = 1L;
        when(tickerRepository.deleteById(ticketId)).thenReturn(Mono.empty());
        StepVerifier.create(ticketService.deleteTicket(ticketId))
                .verifyComplete();
        verify(tickerRepository, times(1)).deleteById(ticketId);
    }
}
