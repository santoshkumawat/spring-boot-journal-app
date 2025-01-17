package com.santoshkumawat.journalApp;

import com.santoshkumawat.journalApp.repository.UserRepositoryImpl;
import com.santoshkumawat.journalApp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JournalAppApplicationTests {

	@Autowired
	private UserRepositoryImpl userRepository;

	@Autowired
	private EmailService emailService;

	@Disabled("tested")
	@Test
	void testSaveNewUser() {
		Assertions.assertNotNull(userRepository.getUsersForSA());
	}

	@Test
	public  void testSendMail(){
		log.info("Sending mail =====================================================================");
		emailService.sendMail("santoshkumawat.sde@gmail.com", "Test Mail", "How are you!");
	}

}
