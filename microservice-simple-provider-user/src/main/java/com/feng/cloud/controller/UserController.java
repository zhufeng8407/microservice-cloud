package com.feng.cloud.controller;

import com.feng.cloud.dao.UserRepository;
import com.feng.cloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/simple/{id}")
    public User findById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(2900);
        return userRepository.findById(id).orElseGet(User::new);
    }
}
