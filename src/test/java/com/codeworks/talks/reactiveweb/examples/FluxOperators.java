package com.codeworks.talks.reactiveweb.examples;

import com.codeworks.talks.reactiveweb.examples.model.FallType;
import com.codeworks.talks.reactiveweb.examples.model.Forecast;
import com.codeworks.talks.reactiveweb.examples.model.Shipment;
import com.codeworks.talks.reactiveweb.examples.model.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static com.codeworks.talks.reactiveweb.examples.model.Forecast.createRandomForecast;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Flux.*;

@Slf4j
public class FluxOperators {

    @Test
    public void operator_zip() {

        Flux<Integer> fluxOf10 = range(1, 10);
        Flux<Long> interval = interval(Duration.ofSeconds(1));

        zip(fluxOf10, interval, (f, i) -> String.format("%d ,%d", f, i))
                .log()
                .blockLast();
    }

    @Test
    public void operator_combineLatest() {

        Flux<Integer> fluxOf10 = range(1, 10);

        Flux<Integer> fluxManufactory = Flux.zip(fluxOf10,
                Flux.interval(Duration.ofSeconds(1)),
                (i, l) -> i);

        Flux<Long> interval = interval(Duration.ofMillis(500));

        Flux.combineLatest(fluxManufactory, interval, (f, i) -> String.format("%d ,%d", f, i))
//                .take(20)
                .log()
                .blockLast();
    }

    @Test
    public void concat() {

        Flux<Shipment> knShipments = just(
                new Shipment("K+N", "HAM", "LON"),
                new Shipment("K+N", "NYC", "SGD"),
                new Shipment("K+N", "CDG", "WAW"),
                new Shipment("K+N", "BKK", "KRA"));

        Flux<Shipment> schenkerShipments = just(
                new Shipment("Schenker", "DUS", "FRA"),
                new Shipment("Schenker", "LAX", "BKK"),
                new Shipment("Schenker", "WAW", "MXP"),
                new Shipment("Schenker", "LON", "KUL"));

        Flux<Shipment> shipments = Flux.concat(knShipments, schenkerShipments)
                .doOnNext(System.out::println);

        StepVerifier.create(shipments)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    public void flatMapAndMap() {

        // Given
        Flux<String> destinations = just("WRO", "WAW", "POZ", "GDA", "HAM", "MUC", "FRA");

        WeatherService weatherService = mock(WeatherService.class);
        when(weatherService.find(isA(String.class)))
                .thenAnswer((Answer<Mono<Forecast>>) invocationOnMock -> {
                    String location = invocationOnMock.getArgument(0);
                    Forecast forecast = createRandomForecast(location);
                    return Mono.just(forecast);
                });

        // When
        destinations
                .flatMap(destination -> weatherService.find(destination)
                        .doOnNext(System.out::println))
                .filter(forecast -> forecast.getGetFallType() != FallType.RAIN)
                .map(forecast -> forecast.getGetFallType() + " in " + forecast.getLocation())
                .doOnNext(System.out::println)
                .blockLast();

        // Then
//        StepVerifier.create(fallInDestination)
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in WRO$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in WAW$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in POZ$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in GDA$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in HAM$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in MUC$"))
////                .expectNextMatches(s -> s.matches("^(RAIN|SNOW) in FRA$"))
//                .verifyComplete();
    }

    @Test
    public void filter() {

    }

    @Test
    public void filterWhen() {

        Flux.range(0, 10)
                .filterWhen(i -> i % 2 == 0 ? Mono.just(true) : Mono.just(false))
                .log()
                .blockLast();

    }

    @Test
    public void switchIfEmpty() {

        // Given
        WeatherService primaryWeatherService = mock(WeatherService.class);
        WeatherService alternativeWeatherService = mock(WeatherService.class);

        String location = "Wrocław";
        Forecast forecastForWroclaw = new Forecast(location, 6.5d, 40, FallType.RAIN);
        when(primaryWeatherService.find(location)).thenReturn(Mono.empty());
        when(alternativeWeatherService.find(location))
                .thenReturn(Mono.just(forecastForWroclaw));

        // When
        Mono<Forecast> forecast = primaryWeatherService.find(location)
                .switchIfEmpty(alternativeWeatherService.find(location))
                .log();


        // Then
        StepVerifier.create(forecast)
                .expectNext(forecastForWroclaw)
                .verifyComplete();
    }

    @Test
    public void operations() {
        Flux<Integer> input = just(1, 1, 3, 4, 5, 3, 2, 3, 2, 1, 2, 3, 4, 5, 2, 3, 4, 5, 6, 2, 4, 5, 6, 7, 3, 3, 2);
//
//        input.sort()
//                .log()
//                .blockLast();

        input.distinct()
                .sort()
                .log()
                .blockLast();
    }

}
