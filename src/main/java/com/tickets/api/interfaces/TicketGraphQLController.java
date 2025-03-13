package com.tickets.api.interfaces;

import com.tickets.api.application.TicketService;
import com.tickets.api.domain.Ticket;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Controller
public class TicketGraphQLController {

    private final TicketService ticketService;

    public TicketGraphQLController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @QueryMapping
    public Flux<Ticket> getAllTickets(@Argument int page, @Argument int size){
        return ticketService.getAllTickets(page, size);
    }

    @QueryMapping
    public Mono<Ticket> getTicketById(@Argument Long id){
        return ticketService.getTicketById(id);
    }

    @QueryMapping
    public Flux<Ticket> getTicketsByStatus(@Argument String status) {
        return ticketService.getTicketsByStatus(status);
    }

    @MutationMapping
    public Mono<Ticket> createTicket(@Argument String usuario, @Argument String status) {
        Ticket ticket = new Ticket(usuario, status);
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setFechaActualizacion(LocalDateTime.now());
        return ticketService.createTicket(ticket);
    }

    @MutationMapping
    public Mono<Ticket> updateTicket(@Argument Long id, @Argument String usuario, @Argument String status) {
        return ticketService.getTicketById(id)
                .flatMap(ticket -> {
                    ticket.setUsuario(usuario);
                    ticket.setStatus(status);
                    ticket.setFechaActualizacion(LocalDateTime.now());
                    return ticketService.updateTicket(id, ticket);
                });
    }

    @MutationMapping
    public Mono<String> deleteTicket(@Argument Long id) {
        return ticketService.getTicketById(id)
                .flatMap(ticket -> ticketService.deleteTicket(id)
                        .thenReturn("Ticket eliminado correctamente"))
                .defaultIfEmpty("Ticket no encontrado");
    }
}
