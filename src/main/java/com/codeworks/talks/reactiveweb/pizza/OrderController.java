package com.codeworks.talks.reactiveweb.pizza;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("orders")
@Slf4j
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void order(@RequestBody Order order) {
        log.info("Creating a new order ...");
        orderService.push(order);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Order> subscribe() {
        return orderService.orders();
    }
}
