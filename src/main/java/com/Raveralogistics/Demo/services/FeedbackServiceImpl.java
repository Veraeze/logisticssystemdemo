package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Feedback;
import com.Raveralogistics.Demo.data.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackRepository feedbackRepository;
    @Override
    public Feedback feedback(String feedbackId, String userId, String bookingId, String feedback) {
        Feedback comment = new Feedback();

        comment.setFeedbackId(feedbackId);
        comment.setUserId(userId);
        comment.setBookingId(bookingId);
        comment.setFeedBack(feedback);

        feedbackRepository.save(comment);
        return comment;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
}
