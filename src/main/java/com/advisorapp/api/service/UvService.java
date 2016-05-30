package com.advisorapp.api.service;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.Uv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UvService {

    private static final Logger log = LoggerFactory.getLogger(UvService.class);

    @Autowired
    private UvRepository uvRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UvService() {
    }

    public Uv createUv(Uv uv) {
        return uvRepository.save(uv);
    }

    public Uv getUv(long id) {
        return uvRepository.findOne(id);
    }

    public void updateUv(Uv uv) {
        uvRepository.save(uv);
    }

    public void deleteUv(Long id) {
        uvRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Uv> getAllUvs(Integer page, Integer size) {
        Page pageOfUvs = uvRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.UvService.getAll.largePayload");
        }
        return pageOfUvs;
    }

    public UvRepository getUvRepository()
    {
        return this.uvRepository;
    }
}
