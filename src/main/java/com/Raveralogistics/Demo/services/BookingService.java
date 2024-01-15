package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.data.model.Customer;
import com.Raveralogistics.Demo.data.model.Sender;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking book(String bookingId, Sender senderInfo, Customer receiverInfo, String userId, String parcelName, BigDecimal cost, LocalDateTime dateTime);
    List<Booking> findAll();

}
