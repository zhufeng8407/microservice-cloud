package com.feng.cloud.consumer.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {

    private long id;

    private String username;

    private String name;

    private short age;

    private BigDecimal balance;

}
