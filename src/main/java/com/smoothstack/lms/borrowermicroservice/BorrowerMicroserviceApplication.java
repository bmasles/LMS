package com.smoothstack.lms.borrowermicroservice;

import com.netflix.discovery.shared.transport.TransportException;
import com.smoothstack.lms.common.util.Debug;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication()
@ComponentScan({"com.smoothstack.lms.common","com.smoothstack.lms" })
@EnableJpaRepositories({"com.smoothstack.lms.common.repository"})
@EntityScan({"com.smoothstack.lms.common.model"})
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

