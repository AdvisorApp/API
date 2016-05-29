package com.advisorapp.api.service;

import com.advisorapp.api.dao.UvTypeRepository;
import com.advisorapp.api.model.Hotel;
import com.advisorapp.api.model.UvType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UvTypeService {

    private static final Logger log = LoggerFactory.getLogger(UvTypeService.class);

    @Autowired
    private UvTypeRepository uvTypeRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UvTypeService() {
    }

    public UvType createUvType(UvType uvType) {
        return uvTypeRepository.save(uvType);
    }

    public UvType getUvType(long id) {
        return uvTypeRepository.findOne(id);
    }

    public void updateUvType(UvType uvType) {
        uvTypeRepository.save(uvType);
    }

    public void deleteUvType(Long id) {
        uvTypeRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<UvType> getAllUvTypes(Integer page, Integer size) {
        Page pageOfUvTypes = uvTypeRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.UvTypeService.getAll.largePayload");
        }
        return pageOfUvTypes;
    }
}
