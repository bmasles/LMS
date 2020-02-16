package com.smoothstack.lms.borrowermicroservice.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MicroservicePingTest {

    @Test
    public void pingMustReturnLocalDateTime() {
        MicroservicePing microservicePing = new MicroservicePing();

        assertNotNull(microservicePing);
        assertNotNull(microservicePing.ping());
        assertEquals(microservicePing.ping().getBody().getClass(), LocalDateTime.class);
    }
}