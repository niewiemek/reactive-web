package com.codeworks.talks.reactiveweb.pizza;

import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

@Service
public class OrderService {

    private final FluxProcessor processor;
    private final FluxSink sink;

    public OrderService() {
        this.processor = DirectProcessor.create().serialize();
        this.sink = processor.sink();
    }

    public void push(Order order) {
        sink.next(order);
    }

    public Flux<Order> orders() {
        return processor;
    }
}
