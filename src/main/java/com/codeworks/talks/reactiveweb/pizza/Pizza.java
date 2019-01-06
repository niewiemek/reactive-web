package com.codeworks.talks.reactiveweb.pizza;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Pizza {

    @Id
    private final String id;
    private final String name;
    private final String description;
    private final Long price;
}
