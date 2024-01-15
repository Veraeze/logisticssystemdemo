package com.Raveralogistics.Demo.dtos.request;

import lombok.Data;
import com.Raveralogistics.Demo.data.model.Customer;
import com.Raveralogistics.Demo.data.model.Sender;

import java.math.BigDecimal;


@Data
public class BookingRequest {
    private Sender senderInfo;
    private Customer receiverInfo;
    private  String userId;
    private String parcelName;
    private BigDecimal cost = BigDecimal.ZERO;
}
