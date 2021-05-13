package com.serguni.todospring.controller;

import com.serguni.todospring.model.User;
import com.serguni.todospring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public void create(@RequestBody User user) {
        userRepository.save(user);
        ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> read(@PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User upUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            upUser.setId(id);
            userRepository.save(upUser);
            return new ResponseEntity<>(upUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            ResponseEntity.ok().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }

}
