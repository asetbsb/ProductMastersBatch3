package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DependencyInjectorConfig.class);

        GreetingService greeting = context.getBean("russianGreetingService", GreetingService.class);
        greeting.sayHello();

        OrderService orderService = context.getBean(OrderService.class);
        OrderService orderServiceSecond = context.getBean(OrderService.class);
        System.out.println("Same OrderService singleton? " + (orderService == orderServiceSecond));

        orderService.makeOrder(BigDecimal.valueOf(15));

        orderService.makeOrder(BigDecimal.valueOf(20), PaymentMethodEnum.MASTERCARD);
        orderService.makeOrder(BigDecimal.valueOf(25), PaymentMethodEnum.BITCOIN);
        orderService.makeOrder(BigDecimal.valueOf(30), PaymentMethodEnum.PLOVCOIN);
    }
}
