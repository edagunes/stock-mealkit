package com.mealkit.stockmicro;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class StockMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMicroApplication.class, args);
    }

}
