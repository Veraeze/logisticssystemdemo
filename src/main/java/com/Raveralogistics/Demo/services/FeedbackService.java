package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback feedback(String feedbackId, String userId, String bookingId, String feedback);
    List<Feedback> findAll();
}
