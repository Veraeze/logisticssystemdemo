package com.Raveralogistics.Demo.data.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Wallet {

    private String cardNumber;
    private String threeDigitNumber;
    private String userId;
    private BigDecimal balance = BigDecimal.ZERO;

}
