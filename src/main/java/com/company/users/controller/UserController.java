package com.company.users.controller;

import com.company.users.dto.User;
import com.company.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody User user) {
        user = userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?>  getUser(@PathVariable @NotBlank @Validated Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?>  updateUser(@RequestBody User user, @NotBlank @Validated @PathVariable Long id) {
        user = userService.updateUser(user,id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //https://dev.to/kreuzwerker/writing-contract-tests-with-pact-in-spring-boot-62l
    //https://reflectoring.io/consumer-driven-contract-provider-pact-spring/

}
