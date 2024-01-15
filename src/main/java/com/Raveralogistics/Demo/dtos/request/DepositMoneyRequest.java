package com.Raveralogistics.Demo.dtos.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositMoneyRequest {

    private BigDecimal amount;
    private String userId;

}
