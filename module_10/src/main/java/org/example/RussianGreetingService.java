package org.example;

public class RussianGreetingService implements GreetingService {
    @Override
    public void sayHello() {
        System.out.println("Привет!");
    }
}
