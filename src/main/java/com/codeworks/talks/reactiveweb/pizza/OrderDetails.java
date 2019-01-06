package com.codeworks.talks.reactiveweb.pizza;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetails {

    private Pizza pizza;
    private String deliverTo;
}
