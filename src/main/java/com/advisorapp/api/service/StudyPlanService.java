package com.advisorapp.api.service;

import com.advisorapp.api.dao.StudyPlanRepository;
import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.Uv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudyPlanService {

    private static final Logger log = LoggerFactory.getLogger(StudyPlanService.class);

    @Autowired
    private StudyPlanRepository studyPlanRepository;

    @Autowired
    private UvRepository uvRepository;

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

    public Set<Uv> getSPNotChosenUVs(long id) {
        StudyPlan sp = studyPlanRepository.findOne(id);

        return this.getRemainingUvOnList(sp.getSemesters(), this.uvRepository.findAll(), sp);
    }

    public Set<Uv> getSPCartNotChosenUVs(long id) {
        StudyPlan sp = studyPlanRepository.findOne(id);

        return this.getRemainingUvOnList(sp.getSemesters(), this.uvRepository.findByAvailableForCart(), sp);
    }

    public Set<Uv> getRemainingUvOnList(Set<Semester> semesters, Iterable<Uv> uvs, StudyPlan studyPlan) {
        Set<Uv> uvsNotChosen = new HashSet<>();

        for (Uv uv : uvs) {
            boolean uvChosen = false;
            for (Semester semester : semesters) {
                if (semester.getUvs().contains(uv)) {
                    uvChosen = true;
                    break;
                }
            }
            if (!uvChosen) uvsNotChosen.add(uv);
        }

        return uvsNotChosen;
    }
}
