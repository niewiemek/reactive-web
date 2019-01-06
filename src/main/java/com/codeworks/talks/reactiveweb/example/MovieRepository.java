package com.codeworks.talks.reactiveweb.example;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class MovieRepository {

    private final List<Movie> movies = Arrays.asList(
            new Movie("Mortal Engines", "tt1571234"),
            new Movie("Spider-Man: Into the Spider-Verse", "tt4633694"),
            new Movie("Aquaman", "tt1477834"));

    public Flux<Movie> findAll() {
        return Flux.fromIterable(movies);
    }
}
