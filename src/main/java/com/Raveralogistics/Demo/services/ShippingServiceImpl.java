package com.Raveralogistics.Demo.services;

import com.Raveralogistics.Demo.data.model.Shipping;
import com.Raveralogistics.Demo.data.repository.ShippingRepository;
import com.Raveralogistics.Demo.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService{
    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Shipping ship(String shippingId, String bookingId, String userId, LocalDateTime dateTime) {
        Shipping shipping = new Shipping();

        shipping.setShippingId(shippingId);
        shipping.setBookingId(bookingId);
        shipping.setUserId(userId);
        shipping.setDateTime(dateTime);
        shipping.setShipped(true);

        shippingRepository.save(shipping);
        return shipping;
    }

    @Override
    public List<Shipping> findAll() {
        return shippingRepository.findAll();
    }
}
