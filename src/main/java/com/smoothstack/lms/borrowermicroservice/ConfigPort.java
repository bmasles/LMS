package com.smoothstack.lms.borrowermicroservice;


import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConfigPort
        implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {



    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        String[] args = BorrowerMicroserviceApplication.getArgs();
        System.out.printf("*** Config %s\n", Arrays.toString(args));
    }
}