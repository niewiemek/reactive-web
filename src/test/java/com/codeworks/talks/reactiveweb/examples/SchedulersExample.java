package com.codeworks.talks.reactiveweb.examples;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class SchedulersExample {

    @Test
    public void publishOnParallel() {
        ParallelFlux<Integer> parallel = Flux.range(1, 10000)
                .parallel()
                .runOn(Schedulers.parallel())
                .log();

        StepVerifier.create(parallel)
                .expectNextCount(10000)
                .verifyComplete();
    }

    @Test
    public void publishOnElastic() {
        ParallelFlux<Integer> parallel = Flux.range(1, 10000)
                .parallel()
                .runOn(Schedulers.elastic())
                .log();

        StepVerifier.create(parallel)
                .expectNextCount(10000)
                .verifyComplete();
    }
}
