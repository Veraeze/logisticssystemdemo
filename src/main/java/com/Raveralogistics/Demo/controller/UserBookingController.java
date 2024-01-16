package com.Raveralogistics.Demo.controller;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.dtos.request.BookingRequest;
import com.Raveralogistics.Demo.dtos.response.ApiResponse;
import com.Raveralogistics.Demo.dtos.response.BookingResponse;
import com.Raveralogistics.Demo.services.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserBookingController {
    @Autowired
    private LogisticService ravera;

    @PostMapping("Sender/book-service")
    public ResponseEntity<?> bookService(@RequestBody BookingRequest bookingRequest){
        BookingResponse bookServiceResponse = new BookingResponse();

        try {
            Booking booking = ravera.bookService(bookingRequest);
            bookServiceResponse.setMessage("Service booked successfully, your booking id is " + booking.getBookingId()+" and the cost is $" + booking.getCost());
            return new ResponseEntity<>(new ApiResponse(true,bookServiceResponse),HttpStatus.ACCEPTED);
        }
        catch (Exception exception){
            bookServiceResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,bookServiceResponse),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("User/booking-history/{username}")
    public Object findBookingBelongingTo (@PathVariable("username") String username){
        try {
            return ravera.findListOfBookingOf(username);
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
}

