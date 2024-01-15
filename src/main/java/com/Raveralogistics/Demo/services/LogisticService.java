package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.data.model.Feedback;
import com.Raveralogistics.Demo.data.model.User;
import com.Raveralogistics.Demo.dtos.request.*;

import java.math.BigDecimal;

public interface LogisticService {
    User register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    User findAccountBelongingTo(String name);

    void logout(LoginRequest loginRequest);

    void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest);

    void withdrawMoneyFromWallet(String userId, BigDecimal bigDecimal);

    Booking bookService(BookingRequest bookingRequest);

    Feedback addFeedback(FeedbackRequest feedbackRequest);

    BigDecimal checkWalletBalance(String userId);

    Object findListOfBookingOf(String username);
}
