package com.feng.cloud.consumer.controller;

import com.feng.cloud.consumer.entity.User;
import com.feng.cloud.consumer.feign.UserFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@Slf4j
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="20000")
    }, threadPoolKey = "findUserPool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value="10"),
            @HystrixProperty(name="maxQueueSize", value="25"),
            @HystrixProperty(name="queueSizeRejectionThreshold", value="2")

    }, fallbackMethod = "defaultFallback")
    @GetMapping("/simple/{id}")
    public User findUserById(@PathVariable Long id) throws InterruptedException {
        log.info("MovieController: findUserById");
        return restTemplate.getForObject("http://localhost:8001/simple/" + id, User.class);
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

    public User defaultFallback(Long id) {
        User user = new User();
        user.setId(-1);

        user.setName("太拥挤了，请稍等！！");
        return user;
    }

}
