package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    Feedback findFeedbackByFeedbackId(String feedbackId);

}
