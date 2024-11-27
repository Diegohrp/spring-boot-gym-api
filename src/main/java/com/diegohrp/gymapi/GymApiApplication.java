package com.diegohrp.gymapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GymApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApiApplication.class, args);
    }

}
