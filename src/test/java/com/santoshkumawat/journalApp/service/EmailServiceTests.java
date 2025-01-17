package com.santoshkumawat.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public  void testSendMail(){
        log.info("Sending mail =====================================================================");
        emailService.sendMail("santoshkumawat.sde@gmail.com", "Test Mail", "How are you!");
    }
}
