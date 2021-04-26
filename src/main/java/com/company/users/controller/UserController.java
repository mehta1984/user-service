package com.company.users.controller;

import com.company.users.dto.User;
import com.company.users.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable @NotBlank @Validated Long id) {
        return userService.findById(id);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User user, @NotBlank @Validated @PathVariable Long id) {
        return userService.updateUser(user,id);
    }

}
