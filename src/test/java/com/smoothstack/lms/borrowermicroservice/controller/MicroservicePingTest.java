package com.smoothstack.lms.borrowermicroservice.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MicroservicePingTest {

    @Test
    public void ping() {
        MicroservicePing microservicePing = new MicroservicePing();

        assertNotNull(microservicePing);
        assertNotNull(microservicePing.ping());

    }
}