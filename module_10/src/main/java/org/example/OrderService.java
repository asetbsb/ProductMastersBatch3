package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Autowired @Qualifier("mastercardProcessor")
    private PaymentProcessor masterCard;

    @Autowired @Qualifier("bitcoinProcessor")
    private PaymentProcessor bitcoin;

    @Autowired @Qualifier("plovCoinProcessor")
    private PaymentProcessor plovCoin;

    public OrderService() {
        System.out.println("Создался OrderService " + this);
    }

    public void makeOrder(BigDecimal amount) {
        paymentProcessor.processPayment(amount);
    }

    public void makeOrder(BigDecimal amount, PaymentMethodEnum method) {
        switch (method) {
            case VISA -> paymentProcessor.processPayment(amount);
            case MASTERCARD -> masterCard.processPayment(amount);
            case BITCOIN -> bitcoin.processPayment(amount);
            case PLOVCOIN -> plovCoin.processPayment(amount);
            default -> throw new IllegalArgumentException("Unknown method: " + method);
        }
    }
}
