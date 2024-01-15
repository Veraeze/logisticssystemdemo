package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

}
