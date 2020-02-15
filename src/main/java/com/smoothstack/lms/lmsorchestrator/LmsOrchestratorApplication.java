package com.smoothstack.lms.lmsorchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class LmsOrchestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsOrchestratorApplication.class, args);
    }

}
