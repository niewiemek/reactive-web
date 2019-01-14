package com.codeworks.talks.reactiveweb.examples.model;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class Forecast {

    private final String location;
    private final Double temperature;
    private final Integer fallPropability;
    private final FallType getFallType;

    public static Forecast createRandomForecast(String location) {
        double temperature = ThreadLocalRandom.current().nextDouble(-30, 30);
        int propability = ThreadLocalRandom.current().nextInt(0, 100);
        FallType type = propability % 2 == 0 ? FallType.RAIN : FallType.SNOW;

        return new Forecast(location, temperature, propability, type);
    }
}
