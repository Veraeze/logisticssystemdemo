package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Shipping;

import java.time.LocalDateTime;
import java.util.List;

public interface ShippingService {
    Shipping ship(String shippingId, String bookingId, String userId, LocalDateTime dateTime);
    List<Shipping> findAll();
}
