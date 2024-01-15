package com.Raveralogistics.Demo.data.repository;

import org.junit.jupiter.api.Test;
import com.Raveralogistics.Demo.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void countIsOneWhenYouSaveOne(){
        User user = new User();
        userRepository.save(user);
        assertEquals(1, userRepository.count());
    }
}