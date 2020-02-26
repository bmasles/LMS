package com.smoothstack.lms.borrowermicroservice;

import com.netflix.discovery.shared.transport.TransportException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
public class BorrowerMicroserviceApplication {

    private static String[] args;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BorrowerMicroserviceApplication.class);

        try {
            app.run(args);
        } catch (TransportException e) {
            Debug.printException(e);
        }


        BorrowerMicroserviceApplication.args = args;

    }

    public static String[] getArgs() {
        return args;
    }
}

