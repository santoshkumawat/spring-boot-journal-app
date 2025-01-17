package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.repository.UserRepository;
import com.santoshkumawat.journalApp.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import com.santoshkumawat.journalApp.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTests {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void  setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Shyam").password("Shyam").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("Shyam");
        Assertions.assertNotNull(user);
    }
}

//@SpringBootTest
//public class UserDetailsServiceImplTest {
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    void loadUserByUsernameTest(){
//        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
//                .thenReturn(User.builder().userName("Shyam").password("Shyam").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailsService.loadUserByUsername("Shyam");
//        Assertions.assertNotNull(user);
//    }
//}
