package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    Feedback findFeedbackByFeedbackId(String feedbackId);

}
