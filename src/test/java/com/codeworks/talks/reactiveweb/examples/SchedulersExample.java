package com.codeworks.talks.reactiveweb.examples;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulersExample {

    @Test
    public void publishOn() {
        Flux.range(1, 10000)
                .publishOn(Schedulers.parallel())
                .log()
                .blockLast();
    }
}
