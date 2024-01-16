package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.repository.ShippingRepository;
import com.Raveralogistics.Demo.dtos.request.PackageRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
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
    @Autowired
    private ShippingRepository shippingRepository;
    @GetMapping("User/track-parcel/{shippingId}")
    public Object track(@PathVariable("shippingId") String shippingId){
        TrackingResponse trackingResponse = new TrackingResponse();

        try {
            return ravera.trackParcel(shippingId);
        }
        catch (Exception exception){
            return exception.getMessage();

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
