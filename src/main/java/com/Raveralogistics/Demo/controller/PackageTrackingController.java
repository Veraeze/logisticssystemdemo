package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.model.User;
import com.Raveralogistics.Demo.dtos.request.PackageRequest;
import com.Raveralogistics.Demo.dtos.request.RegisterRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.RegisterResponse;
import com.Raveralogistics.Demo.dtos.response.TrackingResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PackageTrackingController {
    @Autowired
    private LogisticService ravera;
    @PatchMapping("User/track-parcel")
    public ResponseEntity<?> register(@RequestBody PackageRequest packageRequest){

        TrackingResponse trackingResponse = new TrackingResponse();
        try {
            User user = ravera.track(packageRequest);
            trackingResponse.setMessage("Registration Successful, your user ID is " + user.getUserId());
            return new ResponseEntity<>(new ApiResponse(true, trackingResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            trackingResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, trackingResponse), HttpStatus.BAD_REQUEST);
        }
    }
}
