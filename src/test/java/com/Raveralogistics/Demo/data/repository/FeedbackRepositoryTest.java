package com.Raveralogistics.Demo.data.repository;

import org.junit.jupiter.api.Test;
import com.Raveralogistics.Demo.data.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void countIsOneWhenYouSaveOne(){
        Feedback feedback = new Feedback();
        feedbackRepository.save(feedback);
        assertEquals(1, feedbackRepository.count());
    }

}