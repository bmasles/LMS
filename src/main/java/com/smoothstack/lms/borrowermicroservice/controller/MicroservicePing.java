package com.smoothstack.lms.borrowermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/borrower")
public class MicroservicePing {

    @Autowired
    ServletWebServerApplicationContext server;

    @RequestMapping(method = RequestMethod.GET, path = "/ping")
    ResponseEntity<Map<String, Object>> ping() {

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("localDateTime", LocalDateTime.now());
        model.put("serverPort", server.getWebServer().getPort());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
