package com.santoshkumawat.journalApp.controller;

import com.santoshkumawat.journalApp.entity.User;
import com.santoshkumawat.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("userName/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        Optional<User> byUserName = userService.findByUserName(userName);
        if (byUserName.isPresent()){
            return new ResponseEntity<>(byUserName.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User user){
        try {
            Optional<User> userOptional = userService.findByUserName(userName);
            if (userOptional.isPresent()) {
                userOptional.get().setUserName(user.getUserName().isBlank() ? userOptional.get().getUserName() : user.getUserName());
                userOptional.get().setPassword(user.getPassword());
                userService.saveUser(userOptional.get());
                return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
