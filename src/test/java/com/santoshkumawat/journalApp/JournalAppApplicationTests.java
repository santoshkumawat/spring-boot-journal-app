package com.santoshkumawat.journalApp;

import com.santoshkumawat.journalApp.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JournalAppApplicationTests {

	@Autowired
	private UserRepositoryImpl userRepository;

	@Disabled("tested")
	@Test
	void testSaveNewUser() {
		Assertions.assertNotNull(userRepository.getUsersForSA());
	}

}
