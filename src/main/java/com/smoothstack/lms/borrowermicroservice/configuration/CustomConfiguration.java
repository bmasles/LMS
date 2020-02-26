package com.smoothstack.lms.borrowermicroservice.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.smoothstack.lms")
@EnableTransactionManagement
public class CustomConfiguration {


}
