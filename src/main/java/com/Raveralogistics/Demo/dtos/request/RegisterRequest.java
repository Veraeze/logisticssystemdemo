package com.Raveralogistics.Demo.dtos.request;

import lombok.Data;
import com.Raveralogistics.Demo.data.model.Address;

@Data
public class RegisterRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private Address homeAddress;
}
