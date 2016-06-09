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

import java.util.Optional;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    @Autowired
    AuthenticationService authenticationService;

    public UserService() {
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User signUp(User user) {
        user.setPassword(authenticationService.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(long id) {
        User one = userRepository.findOne(id);
        return one;
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

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public Optional<User> fetchByCredentials(Credential credential) {
        return Optional.ofNullable(
                userRepository.findUserByEmailAndPassword(
                        credential.getEmail(),
                        authenticationService.hashPassword(credential.getPassword())
                )
        );
    }

    public User findById(long id) {
        User userById = userRepository.findUserById(id);
        return userById;
    }
}
