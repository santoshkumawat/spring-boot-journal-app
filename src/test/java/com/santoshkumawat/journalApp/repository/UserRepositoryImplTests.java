package com.santoshkumawat.journalApp.repository;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void testSaveNewUser() {
        Assertions.assertNotNull(userRepository.getUsersForSA());
    }
}
