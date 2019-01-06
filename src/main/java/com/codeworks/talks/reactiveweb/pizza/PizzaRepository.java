package com.codeworks.talks.reactiveweb.pizza;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PizzaRepository extends ReactiveCrudRepository<Pizza, String> {

    Mono<Pizza> findFirstByName(String name);
}
