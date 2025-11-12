package org.example;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
@Configuration
@ComponentScan(basePackages = "org.example")
public class DependencyInjectorConfig {
    @Bean("englishGreetingService")
    public GreetingService englishGreetingService() {
        return new EnglishGreetingService();
    }

    @Bean("russianGreetingService")
    public GreetingService russianGreetingService() {
        return new RussianGreetingService();
    }
}

