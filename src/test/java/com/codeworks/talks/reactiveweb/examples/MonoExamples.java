package com.codeworks.talks.reactiveweb.examples;

import org.junit.Test;
import reactor.core.publisher.Mono;

import static java.time.Duration.ofMillis;
import static reactor.core.publisher.Mono.delay;
import static reactor.core.publisher.Mono.first;

public class MonoExamples {

    @Test
    public void mono() {
        Object return2 = Mono.empty()
                .log()
                .switchIfEmpty(Mono.just(3))
                .block();

        System.out.println(return2);
    }

    @Test
    public void error() {
        Object result = Mono.just(1)
                .log()
                .map(i -> {
                    throw new NullPointerException();
//                    return i;
                })
                .doOnError(err -> System.out.println("error!"))
//                .onError
                .onErrorReturn(2)
                .block();

        System.out.println(result);
    }

    @Test
    public void selectFirst() {

        first(
                delay(ofMillis(300)).just(1),
                delay(ofMillis(200)).just(2))
                .log()
                .block();
    }

}
