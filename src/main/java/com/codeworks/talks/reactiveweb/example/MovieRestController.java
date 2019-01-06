package com.codeworks.talks.reactiveweb.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
@RequestMapping("movies")
@CrossOrigin
public class MovieRestController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieRestController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "immediateAll")
    public Flux<Movie> immediateAll() {
        return streamAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "streamAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> streamAll() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));
        Flux<Movie> fluxOfMovies = movieRepository.findAll();

        return Flux.zip(interval, fluxOfMovies)
                .map(Tuple2::getT2);
    }
}
