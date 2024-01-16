package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.dtos.request.FeedbackRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.FeedbackResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReviewController {
    @Autowired
    private LogisticService ravera;
    @PostMapping("User/feedback")
    public ResponseEntity<?> addReview(@RequestBody FeedbackRequest feedbackRequest){
        FeedbackResponse reviewResponse = new FeedbackResponse();

        try {
            ravera.addFeedback(feedbackRequest);
            reviewResponse.setMessage("Feedback successfully sent");
            return  new ResponseEntity<>(new ApiResponse(true,reviewResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            reviewResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,reviewResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("User/view-user-reviews")
    public Object findReviews (){
        try {
            return ravera.findListOfFeedback();
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
