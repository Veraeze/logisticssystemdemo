package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.model.User;
import com.Raveralogistics.Demo.dtos.request.LoginRequest;
import com.Raveralogistics.Demo.dtos.request.RegisterRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.LoginResponse;
import com.Raveralogistics.Demo.dtos.response.RegisterResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {
    @Autowired
    private LogisticService ravera;

    @PostMapping("Sender/Sign-up")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){

        RegisterResponse registerResponse = new RegisterResponse();
        try {
            User user = ravera.register(registerRequest);
            registerResponse.setMessage("Registration Successful, your user ID is " + user.getUserId());
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            registerResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("Sender/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();

        try {
            ravera.login(loginRequest);
            loginResponse.setMessage("login successful");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            loginResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,loginResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("Sender/logout")
    public ResponseEntity<?> logout(@RequestBody LoginRequest logoutRequest){
        LoginResponse loginResponse = new LoginResponse();

        try {
            ravera.login(logoutRequest);
            loginResponse.setMessage("logout successful");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            loginResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,loginResponse),HttpStatus.BAD_REQUEST);
        }
    }
}
