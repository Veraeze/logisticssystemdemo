package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface BookingRepository extends MongoRepository<Booking, String> {
    Booking findBookingByBookingId(String id);
}
