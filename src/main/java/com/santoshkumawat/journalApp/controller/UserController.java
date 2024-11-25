package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.api.response.WeatherResponse;
import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.service.UserService;
import com.santoshkumawat.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            Optional<User> userOptional = userService.findByUserName(userName);
            if (userOptional.isPresent()) {
                userOptional.get().setUserName(user.getUserName().isBlank() ? userOptional.get().getUserName() : user.getUserName());
                userOptional.get().setPassword(user.getPassword());
                userService.saveNewUser(userOptional.get());
                return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> greeting() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            WeatherResponse weather = weatherService.getWeather("New Delhi");
            String greetingMessage = "";
            if(weather != null) {
                greetingMessage = ", Weather feels like " + weather.getCurrent().getFeelslike();
            }
            return new ResponseEntity<>("Hi " + authentication.getName() + greetingMessage, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Something went wrong: ", HttpStatus.BAD_REQUEST);
        }
    }
}
