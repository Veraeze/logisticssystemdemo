package com.Raveralogistics.Demo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Feedback {

    @Id
    private String feedbackId;
    private String userId;
    private String bookingId;
    private String feedBack;

}
