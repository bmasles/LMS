package com.smoothstack.lms.borrowermicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/borrower")
public class MicroservicePing {

    @RequestMapping(method = RequestMethod.GET, path = "/ping")
    ResponseEntity<LocalDateTime> ping() {
        return new ResponseEntity<LocalDateTime>(LocalDateTime.now(), HttpStatus.OK );
    }

}
