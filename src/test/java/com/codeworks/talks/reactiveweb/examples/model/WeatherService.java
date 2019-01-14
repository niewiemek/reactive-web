package com.codeworks.talks.reactiveweb.examples.model;

import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<Forecast> find(String location);
}

