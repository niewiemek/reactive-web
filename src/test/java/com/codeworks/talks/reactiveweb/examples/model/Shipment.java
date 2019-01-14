package com.codeworks.talks.reactiveweb.examples.model;

import lombok.Data;

@Data
public class Shipment {

    private final String provider;
    private final String source;
    private final String destination;
}
