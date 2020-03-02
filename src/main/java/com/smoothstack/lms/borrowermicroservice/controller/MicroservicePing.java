package com.smoothstack.lms.borrowermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping( {"/borrower","/borrowers"} )
public class MicroservicePing {


    @Autowired
    ServletWebServerApplicationContext server;

    @Autowired
    Environment environment;

    @RequestMapping(method = RequestMethod.GET, path = {"/ping", "/port"})
    ResponseEntity<Map<String, Object>> ping(Principal principal) {

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("localDateTime", LocalDateTime.now());
        model.put("webServerPort", server.getWebServer().getPort());
        model.put("localServerPort", environment.getProperty("local.server.port"));
        model.put("principalName", principal != null?principal.getName():"<not logged-in>");
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
