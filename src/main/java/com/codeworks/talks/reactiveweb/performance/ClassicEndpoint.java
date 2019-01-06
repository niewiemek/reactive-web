package com.codeworks.talks.reactiveweb.performance;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("performance/classic")
@RestController
public class ClassicEndpoint {

    @RequestMapping("calculate")
    @SneakyThrows
    public Integer calculate() {
        Thread.sleep(1000);

        return 1;
    }
}
