package com.advisorapp.api.service;

import com.advisorapp.api.dao.UserRepository;
import com.advisorapp.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.advisorapp.api.model.Credential;
import org.springframework.stereotype.Service;

<<<<<<< efc668c7dd7bde8971b0dd96d18322c7f1f17ac3
=======
import java.util.Optional;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
>>>>>>> [ADV-16] Create an AuthenticationService to provide and verify a jwt
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UserService() {
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<User> getAllUsers(Integer page, Integer size) {
        Page pageOfUsers = userRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.UserService.getAll.largePayload");
        }
        return pageOfUsers;
    }

    public UserRepository userRepository() {
        return this.userRepository;
    }

    public Optional<User> fetchByCredentials(Credential credential) {
        return Optional.of(new User());
    }
}
