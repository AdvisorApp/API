package com.advisorapp.api.service;

import com.advisorapp.api.dao.UvUserRepository;
import com.advisorapp.api.model.UvUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UvUserService {

    private static final Logger log = LoggerFactory.getLogger(UvUserService.class);

    @Autowired
    private UvUserRepository uvUserRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UvUserService() {
    }

    public UvUser createUvUser(UvUser uvUser) {
        return uvUserRepository.save(uvUser);
    }

    public UvUser getUvUser(long id) {
        return uvUserRepository.findOne(id);
    }

    public void updateUvUser(UvUser uvUser) {
        uvUserRepository.save(uvUser);
    }

    public void deleteUvUser(Long id) {
        uvUserRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<UvUser> getAllUvUsers(Integer page, Integer size) {
        Page pageOfUvUsers = uvUserRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.UvUserService.getAll.largePayload");
        }
        return pageOfUvUsers;
    }
}
