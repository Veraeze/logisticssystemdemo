package com.Raveralogistics.Demo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private String userId;
    private Customer customer;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Address homeAddress;
    private  boolean isLoggedIn;
    private Wallet wallet;

}
