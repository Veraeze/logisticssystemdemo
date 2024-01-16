package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageTrackingController {
    @Autowired
    private LogisticService ravera;
    @GetMapping("User/track-parcel/{shippingId}")
    public Object track(@PathVariable("shippingId") String shippingId){

        try {
            return ravera.trackParcel(shippingId);
        }
        catch (Exception exception){
            return exception.getMessage();

        }
    }

}
