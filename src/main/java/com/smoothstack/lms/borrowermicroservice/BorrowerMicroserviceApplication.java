package com.smoothstack.lms.borrowermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BorrowerMicroserviceApplication {

    private static String[] args;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BorrowerMicroserviceApplication.class);

        app.run(args);

        BorrowerMicroserviceApplication.args = args;

    }

    public static String[] getArgs() {
        return args;
    }
}

