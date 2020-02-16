package com.smoothstack.lms.borrowermicroservice;
import static org.assertj.core.api.Assertions.assertThat;
import com.smoothstack.lms.borrowermicroservice.controller.MicroservicePing;
import com.smoothstack.lms.borrowermicroservice.controller.MicroservicePingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BorrowerMicroserviceApplicationTests {

    private MicroservicePingTest microservicePingTest = new MicroservicePingTest();

    @Test
    void contextLoads() {
        microservicePingTest.pingMustReturnLocalDateTime();
    }


}
