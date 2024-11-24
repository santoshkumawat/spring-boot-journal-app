package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.entity.JournalEntry;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.repository.JournalEntryRepository;
import com.santoshkumawat.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

//    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUserName(String userName){
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public Boolean saveNewUser(User user) {
        try {
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            LOGGER.error("something went wrong: User already exist");
            log.error("something went wrong: User already exist");
        }
        return false;
    }

    public void saveAdmin(User user){
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUserById(ObjectId id){
        userRepository.deleteById(id);
    }

    public void deleteByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
}
