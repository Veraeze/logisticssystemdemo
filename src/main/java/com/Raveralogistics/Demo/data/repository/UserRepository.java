package com.Raveralogistics.Demo.data.repository;

import com.Raveralogistics.Demo.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserBy(String name);
}
