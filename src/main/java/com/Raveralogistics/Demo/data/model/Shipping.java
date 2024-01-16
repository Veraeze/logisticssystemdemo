package com.Raveralogistics.Demo.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Shipping {

    @Id
    private String shippingId;
    private String bookingId;
    private String userId;
    private LocalDateTime dateTime;
    private boolean isShipped;
    private boolean isDelivered;
    private List<PackageHistory> history = new ArrayList<>();

}
