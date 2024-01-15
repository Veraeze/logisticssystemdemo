package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

    public interface BookingRepository extends MongoRepository<Booking, String> {
        Booking findBookingByBookingId(String id);
    }

