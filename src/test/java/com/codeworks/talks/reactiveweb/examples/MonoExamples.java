package com.codeworks.talks.reactiveweb.examples;

import org.junit.Test;

import static java.time.Duration.ofMillis;
import static reactor.core.publisher.Mono.delay;
import static reactor.core.publisher.Mono.first;

public class MonoExamples {

    @Test
    public void selectFirst() {

        first(
                delay(ofMillis(300)).just(1),
                delay(ofMillis(200)).just(2))
                .log()
                .block();
    }

}
