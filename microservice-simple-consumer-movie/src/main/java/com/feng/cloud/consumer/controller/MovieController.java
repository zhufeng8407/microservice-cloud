package com.feng.cloud.consumer.controller;

import com.feng.cloud.consumer.entity.User;
import com.feng.cloud.consumer.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/simple/{id}")
    public User findUserById(@PathVariable Long id) {
        return restTemplate.getForObject("http://localhost:8001/simple/" + id, User.class);
    }

    @GetMapping("/client/{id}")
    public User findUserById2(@PathVariable Long id) {
        return userFeignClient.findById(id);
    }

}
