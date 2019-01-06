package com.codeworks.talks.reactiveweb.functional;

import com.codeworks.talks.reactiveweb.pizza.Pizza;
import com.codeworks.talks.reactiveweb.pizza.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalInterface {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Bean
    RouterFunction<ServerResponse> findPizzaByIdRoute() {
        return route(GET("/functional/pizza/{id}"),
                req -> ok().body(
                        pizzaRepository.findById(req.pathVariable("id")), Pizza.class))
                .and(route(PUT("/functional/pizza"),
                        req -> req.bodyToMono(Pizza.class)
                                .doOnNext(pizzaRepository::save)
                                .then(ok().build())));
    }
}
