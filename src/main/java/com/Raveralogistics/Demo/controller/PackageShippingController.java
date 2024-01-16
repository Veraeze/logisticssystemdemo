package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.model.Shipping;
import com.Raveralogistics.Demo.dtos.request.PackageRequest;
import com.Raveralogistics.Demo.dtos.request.ShippingRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.TrackingResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageShippingController {
    @Autowired
    private LogisticService ravera;

    @PostMapping("Admin/ship-parcel")
    public ResponseEntity<?> ship(@RequestBody ShippingRequest shippingRequest){

        TrackingResponse trackingResponse = new TrackingResponse();
        try {
            Shipping user = ravera.shipParcel(shippingRequest);
            trackingResponse.setMessage("Your parcel has been shipped, your shipping ID is " + user.getShippingId());
            return new ResponseEntity<>(new ApiResponse(true, trackingResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            trackingResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, trackingResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("Admin/update-tracking-info")
    public ResponseEntity<?> update(@RequestBody PackageRequest packageRequest){

        TrackingResponse trackingResponse = new TrackingResponse();
        try {
            ravera.updateTrackingInfo(packageRequest);
            trackingResponse.setMessage(packageRequest.getDateTime() + " \n " +packageRequest.getAction());
            return new ResponseEntity<>(new ApiResponse(true, trackingResponse), HttpStatus.CREATED);
        }
        catch (Exception exception){
            trackingResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, trackingResponse), HttpStatus.BAD_REQUEST);
        }
    }
}
