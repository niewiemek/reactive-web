package com.codeworks.talks.reactiveweb.performance;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("performance/flux")
public class FluxEndpoint {

    @RequestMapping("calculate")
    public Mono<Integer> calculate() {
        return Mono.just(1).delayElement(Duration.ofSeconds(1));
    }
}
