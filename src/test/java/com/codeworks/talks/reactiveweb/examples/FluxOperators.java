package com.codeworks.talks.reactiveweb.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
public class FluxOperators {

    @Test
    public void zip() {

        Flux<Integer> fluxOf10 = Flux.fromStream(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 10));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux.zip(fluxOf10, interval, (f, i) -> String.format("%d ,%d", f, i))
                .log()
                .blockLast();
    }

    @Test
    public void zip_with_mono() {

        Flux<Integer> fluxOf10 = Flux.fromStream(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 10));
        Mono<String> single = Mono.just("single");

        Flux.zip(fluxOf10, single, (f, s) -> String.format("%d ,%s", f, s))
                .log()
                .blockLast();
    }

    @Test
    public void combineLatest() {

        Flux<Integer> fluxOf10 = Flux.fromStream(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 10));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux.combineLatest(fluxOf10, interval, (f, i) -> String.format("%d ,%d", f, i))
                .take(20)
                .log()
                .blockLast();
    }


    @Test
    public void combineLatest_with_mono() {

        Flux<Integer> fluxOf10 = Flux.fromStream(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 10));
        Mono<String> single = Mono.just("single");

        Flux.combineLatest(fluxOf10, single, (f, s) -> String.format("%d ,%s", f, s))
                .log()
                .blockLast();

    }

    @Test
    public void concat() {

        Flux<Integer> fluxOf10 = Flux.fromStream(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 10));
        Mono<String> single = Mono.just("single");

        Flux.concat(fluxOf10, single)
                .log()
                .blockLast();
    }

    @Test
    public void flatMapAndMap() {

        Flux<Integer> integerFlux = Flux.fromStream(Stream.of(1, 2, 3, 4, 5));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux.zip(integerFlux, interval)
                .map(Tuple2::getT1)
                .flatMap(Mono::just)
                .log()
                .blockLast();

    }

    @Test
    public void switchIfEmpty() {
        Mono.empty()
                .flux()
                .switchIfEmpty(Flux.just(1, 2, 3, 4, 5))
                .log()
                .blockLast();
    }

    @Test
    public void operations() {
        Flux<Integer> input = Flux.just(1, 1, 3, 4, 5, 3, 2, 3, 2, 1, 2, 3, 4, 5, 2, 3, 4, 5, 6, 2, 4, 5, 6, 7, 3, 3, 2);

        input.sort()
                .log()
                .blockLast();

        input.distinct()
                .sort()
                .log()
                .blockLast();
    }

    @Test
    public void testingFlux() {
        Flux<Integer> fluxRange = Flux.range(1, 5);

        StepVerifier.create(fluxRange)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

        StepVerifier.create(fluxRange)
                .expectNextCount(5)
                .verifyComplete();
    }
}
