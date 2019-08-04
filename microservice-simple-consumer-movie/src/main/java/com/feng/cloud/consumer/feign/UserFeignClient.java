package com.feng.cloud.consumer.feign;

import com.feng.cloud.consumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("provider-user")
public interface UserFeignClient {

    @GetMapping("/simple/{id}")
    User findById(@PathVariable Long id);

}
