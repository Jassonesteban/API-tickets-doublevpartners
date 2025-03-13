package com.tickets.api.domain;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TickerRepository extends ReactiveCrudRepository<Ticket, Long> {
    Flux<Ticket> findByStatus(String status);

    @Query("SELECT * FROM tickets ORDER BY fecha_creacion DESC LIMIT :size OFFSET :offset")
    Flux<Ticket> findAllWithPagination(int size, int offset);

    @Query("SELECT COUNT(*) FROM tickets")
    Mono<Long> countTickets();
}
