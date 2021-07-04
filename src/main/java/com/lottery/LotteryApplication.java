package com.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class LotteryApplication {

    public static void main(String[] args){
        SpringApplication.run(LotteryApplication.class, args);
    }
}
