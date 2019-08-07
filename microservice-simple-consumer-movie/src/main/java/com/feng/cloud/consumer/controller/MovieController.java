package com.feng.cloud.consumer.controller;

import com.feng.cloud.consumer.entity.User;
import com.feng.cloud.consumer.feign.UserFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
    })
    @GetMapping("/simple/{id}")
    public String findUserById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(2900);
        return restTemplate.getForObject("http://localhost:8001/simple/" + id, String.class);
    }

    @GetMapping("/client/{id}")
    public User findUserById2(@PathVariable Long id) {
        return userFeignClient.findById(id);
    }

    public User fallBackFindUser(Long id) {
        User user = new User();
        user.setName("太拥挤了，请稍等！！");
        return user;
    }

    public String defaultFallback(Long id) {
        return "太拥挤了，请稍等！！";
    }

}
