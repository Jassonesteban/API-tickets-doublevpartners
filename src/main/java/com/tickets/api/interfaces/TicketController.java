package com.tickets.api.interfaces;

import com.tickets.api.application.TicketService;
import com.tickets.api.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public Flux<Ticket> getAllTickets(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        return ticketService.getAllTickets(page,size);
    }

    @GetMapping("/count")
    public Mono<Long> getTotalTickets(){
        return ticketService.getTotalTickets();
    }

    @GetMapping("/{id}")
    public Mono<Ticket> getTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id);
    }

    @GetMapping("/status/{status}")
    public Flux<Ticket> getTicketsByStatus(@PathVariable String status){
        return ticketService.getTicketsByStatus(status);
    }

    @PostMapping
    public Mono<ResponseEntity<Ticket>> createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket)
                .map(savedTicket -> ResponseEntity.status(201).body(savedTicket));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Ticket>> updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails){
        return ticketService.updateTicket(id, ticketDetails)
                .map(updateTicket -> ResponseEntity.status(HttpStatus.OK).body(updateTicket))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteTicket(@PathVariable Long id){
        return ticketService.deleteTicket(id)
                .then(Mono.just(ResponseEntity.ok("Ticket eliminado correctamente")))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado"));
    }
}
