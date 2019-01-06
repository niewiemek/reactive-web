package com.codeworks.talks.reactiveweb.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("pizza")
@RestController
@CrossOrigin
public class PizzaController {

    private final PizzaRepository repository;

    @Autowired
    public PizzaController(PizzaRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Pizza> all() {
        return this.repository.findAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Pizza> byName(@PathVariable String name) {
        return this.repository.findFirstByName(name);
    }
}
