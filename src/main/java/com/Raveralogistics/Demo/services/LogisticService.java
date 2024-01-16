package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.*;
import com.Raveralogistics.Demo.dtos.request.*;

import java.math.BigDecimal;
import java.util.List;

public interface LogisticService {
    User register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    User findAccountBelongingTo(String name);

    void logout(LoginRequest loginRequest);

    void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest);

    void withdrawMoneyFromWallet(String userId, BigDecimal bigDecimal);

    Booking bookService(BookingRequest bookingRequest);

    Feedback addFeedback(FeedbackRequest feedbackRequest);

    Object findListOfFeedback();

    BigDecimal checkWalletBalance(String userId);

    Object findListOfBookingOf(String username);

    void updateTrackingInfo(PackageRequest packageRequest);

    List<PackageHistory> trackParcel(String Id);

    Shipping shipParcel(ShippingRequest shippingRequest);
}
