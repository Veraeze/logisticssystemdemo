package com.Raveralogistics.Demo.utils;

import com.Raveralogistics.Demo.data.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Mapper {
    public static User map(String name, String password, String phoneNumber, String email, Address homeAddress, String userId){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setHomeAddress(homeAddress);
        user.setUserId(userId);
        return user;
    }
    public static Booking map(String bookingId, Sender senderInfo,
                              Customer receiverInfo, String parcelName, BigDecimal cost, LocalDateTime dateTime){
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setSenderInfo(senderInfo);
        booking.setReceiverInfo(receiverInfo);
        booking.setParcelName(parcelName);
        booking.setCost(cost);
        return booking;
    }
}
