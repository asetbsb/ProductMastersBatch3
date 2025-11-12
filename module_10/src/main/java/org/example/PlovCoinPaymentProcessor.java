package org.example;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("plovCoinProcessor")
public class PlovCoinPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(BigDecimal amount) {
        System.out.println("Оплачиваю через PlovCoin: " + amount);
    }
}
