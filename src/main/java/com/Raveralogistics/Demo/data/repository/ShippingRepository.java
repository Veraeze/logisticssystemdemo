package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Shipping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShippingRepository extends MongoRepository<Shipping, String> {
    Shipping findShippingByShippingId(String id);

}
