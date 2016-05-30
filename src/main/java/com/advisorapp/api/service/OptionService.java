package com.advisorapp.api.service;

import com.advisorapp.api.dao.OptionRepository;
import com.advisorapp.api.model.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private static final Logger log = LoggerFactory.getLogger(OptionService.class);

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public OptionService() {
    }

    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    public Option getOption(long id) {
        return optionRepository.findOne(id);
    }

    public void updateOption(Option option) {
        optionRepository.save(option);
    }

    public void deleteOption(Long id) {
        optionRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Option> getAllOptions(Integer page, Integer size) {
        Page pageOfOptions = optionRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.OptionService.getAll.largePayload");
        }
        return pageOfOptions;
    }
}
