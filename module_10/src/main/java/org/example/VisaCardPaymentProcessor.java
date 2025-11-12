package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("visaProcessor")
@Primary // default bean when multiple PaymentProcessor exist
public class VisaCardPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(BigDecimal amount) {
        System.out.println("Оплачиваю через VISA карту: " + amount);
    }
}
