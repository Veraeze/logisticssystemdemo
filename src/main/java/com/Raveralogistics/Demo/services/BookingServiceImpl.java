package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Booking;
import com.Raveralogistics.Demo.data.model.Customer;
import com.Raveralogistics.Demo.data.model.Sender;
import com.Raveralogistics.Demo.data.repository.BookingRepository;
import com.Raveralogistics.Demo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Booking book(String bookingId, Sender senderInfo, Customer receiverInfo, String userId, String parcelName, LocalDateTime dateTime) {
        Booking booking = new Booking();

        booking.setBookingId(bookingId);
        booking.setSenderInfo(senderInfo);
        booking.setReceiverInfo(receiverInfo);
        booking.setUserId(userId);
        booking.setParcelName(parcelName);
        booking.setDateTime(dateTime);
        booking.setBooked(true);

        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
