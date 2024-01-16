package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.data.model.User;
import com.Raveralogistics.Demo.dtos.request.*;
import com.Raveralogistics.Demo.dtos.response.*;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserLogisticsController {
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

    @PostMapping("Sender/deposit-into-wallet")
    public ResponseEntity<?> depositMoneyIntoWallet(@RequestBody DepositMoneyRequest depositMoneyRequest){
        WalletResponse walletResponse = new WalletResponse();

        try {
            ravera.depositMoneyIntoWallet(depositMoneyRequest);
            walletResponse.setMessage(depositMoneyRequest.getAmount() + " has been deposited successfully");
            return new ResponseEntity<>(new ApiResponse(true,walletResponse),HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            walletResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,walletResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("Sender/book-service")
    public ResponseEntity<?> bookService(@RequestBody BookingRequest bookingRequest){
        BookingResponse bookServiceResponse = new BookingResponse();

        try {
            Booking booking = ravera.bookService(bookingRequest);
            bookServiceResponse.setMessage("Service booked successfully, your booking id is " + booking.getBookingId()+" and the cost is $" + booking.getCost());
            return new ResponseEntity<>(new ApiResponse(true,bookServiceResponse),HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            bookServiceResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,bookServiceResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("User/feedback")
    public ResponseEntity<?> addReview(@RequestBody FeedbackRequest feedbackRequest){
        FeedbackResponse reviewResponse = new FeedbackResponse();

        try {
            ravera.addFeedback(feedbackRequest);
            reviewResponse.setMessage("Feedback successfully sent");
            return  new ResponseEntity<>(new ApiResponse(true,reviewResponse),HttpStatus.CREATED);
        }
        catch (Exception exception){
            reviewResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,reviewResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("Sender/wallet-balance/{userId}")
    public ResponseEntity<?> checkWalletBalance(@PathVariable("userId") String userId) {
        WalletBalanceResponse balanceResponse = new WalletBalanceResponse();

        try {
            BigDecimal balance = ravera.checkWalletBalance(userId);
            balanceResponse.setMessage("Your balance is $" + balance);
            return new ResponseEntity<>(new ApiResponse(true, balanceResponse), HttpStatus.ACCEPTED);
        }
        catch (Exception exception) {
            balanceResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, balanceResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/booking history/{username}")
    public Object findBookingBelongingTo (@PathVariable("username") String username){
        try {
            return ravera.findListOfBookingOf(username);
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
}

