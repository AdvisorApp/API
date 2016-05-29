package com.advisorapp.api.service;

import com.advisorapp.api.dao.SemesterRepository;
import com.advisorapp.api.model.Hotel;
import com.advisorapp.api.model.Semester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SemesterService {

    private static final Logger log = LoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public SemesterService() {
    }

    public Semester createSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    public Semester getSemester(long id) {
        return semesterRepository.findOne(id);
    }

    public void updateSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void deleteSemester(Long id) {
        semesterRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Semester> getAllSemesters(Integer page, Integer size) {
        Page pageOfSemesters = semesterRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("advisorapp.SemesterService.getAll.largePayload");
        }
        return pageOfSemesters;
    }
}
