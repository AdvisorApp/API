package com.advisorapp.api.service;

import com.advisorapp.api.dao.StudyPlanRepository;
import com.advisorapp.api.model.StudyPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudyPlanService {

    private static final Logger log = LoggerFactory.getLogger(StudyPlanService.class);

    @Autowired
    private StudyPlanRepository studyPlanRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public StudyPlanService() {
    }

    public StudyPlan createStudyPlan(StudyPlan studyPlan) {
        return studyPlanRepository.save(studyPlan);
    }

    public StudyPlan getStudyPlan(long id) {
        return studyPlanRepository.findOne(id);
    }

    public void updateStudyPlan(StudyPlan studyPlan) {
        studyPlanRepository.save(studyPlan);
    }

    public void deleteStudyPlan(Long id) {
        studyPlanRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<StudyPlan> getAllStudyPlans(Integer page, Integer size) {
        Page pageOfStudyPlans = studyPlanRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.StudyPlanService.getAll.largePayload");
        }
        return pageOfStudyPlans;
    }
}
