package com.smoothstack.lms.borrowermicroservice.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.smoothstack.lms","com.smoothstack.lms.common"})
@EnableTransactionManagement
public class CustomConfiguration {


}
