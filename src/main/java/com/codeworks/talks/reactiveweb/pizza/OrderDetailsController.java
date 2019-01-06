package com.codeworks.talks.reactiveweb.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin
@RequestMapping("orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderDetails> orderDetails() {
        return orderService.orders()
                .flatMap(
                        order -> pizzaRepository.findById(order.getPizzaId())
                                .map(pizza -> createOrderDetails(order, pizza)));
    }

    private OrderDetails createOrderDetails(Order order, Pizza pizza) {
        return OrderDetails.builder()
                .pizza(pizza)
                .deliverTo(order.getDeliverTo())
                .build();
    }


}
