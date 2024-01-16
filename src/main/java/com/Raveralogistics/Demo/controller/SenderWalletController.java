package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.dtos.request.DepositMoneyRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.WalletBalanceResponse;
import com.Raveralogistics.Demo.dtos.response.WalletResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class SenderWalletController {
    @Autowired
    private LogisticService ravera;

    @PostMapping("Sender/deposit-into-wallet")
    public ResponseEntity<?> depositMoneyIntoWallet(@RequestBody DepositMoneyRequest depositMoneyRequest){
        WalletResponse walletResponse = new WalletResponse();

        try {
            ravera.depositMoneyIntoWallet(depositMoneyRequest);
            walletResponse.setMessage(depositMoneyRequest.getAmount() + " has been deposited successfully");
            return new ResponseEntity<>(new ApiResponse(true,walletResponse), HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            walletResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,walletResponse),HttpStatus.BAD_REQUEST);
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
}
