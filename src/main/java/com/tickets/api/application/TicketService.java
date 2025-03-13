package com.tickets.api.application;

import com.tickets.api.domain.TickerRepository;
import com.tickets.api.domain.Ticket;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TickerRepository tickerRepository;

    public TicketService(TickerRepository tickerRepository) {
        this.tickerRepository = tickerRepository;
    }

    public Flux<Ticket> getAllTickets(int page, int size){
        int offset = page * size;
        return tickerRepository.findAllWithPagination(size, offset);
    }

    public Mono<Long> getTotalTickets(){
        return tickerRepository.countTickets();
    }

    public Mono<Ticket> getTicketById(Long id){
        return tickerRepository.findById(id);
    }

    public Flux<Ticket> getTicketsByStatus(String status){
        return tickerRepository.findByStatus(status);
    }

    public Mono<Ticket> createTicket(Ticket ticket){
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setFechaActualizacion(LocalDateTime.now());
        return tickerRepository.save(ticket);
    }

    public Mono<Ticket> updateTicket(Long id, Ticket ticketDetails){
        return tickerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Ticket no encontrado")))
                .flatMap(ticket -> {
                    if(ticketDetails.getStatus() != null){
                        ticket.setStatus(ticketDetails.getStatus());
                    }

                    if(ticketDetails.getUsuario() != null){
                        ticket.setUsuario(ticketDetails.getUsuario());
                    }
                    ticket.setFechaActualizacion(LocalDateTime.now());
                    return tickerRepository.save(ticket);
                });
    }

    public Mono<Void> deleteTicket(Long id){
        return tickerRepository.deleteById(id);
    }
}
