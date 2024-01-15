package com.Raveralogistics.Demo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Booking {

    @Id
    private String bookingId;
    private Sender senderInfo;
    private Customer receiverInfo;
    private  boolean isBooked;
    private String parcelName;
    private LocalDateTime dateTime;
    private String userId;
}
