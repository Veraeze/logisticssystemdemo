package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AddressRepository extends MongoRepository<Address, String> {

}
