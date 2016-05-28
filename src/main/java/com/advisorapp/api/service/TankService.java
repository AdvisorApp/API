package com.advisorapp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TankService {
    @Autowired
    private OptionService optionService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private UserService userService;

    @Autowired
    private UvService uvService;

    @Autowired
    private UvUserService uvUserService;

    public OptionService getOptionService() {
        return optionService;
    }

    public SemesterService getSemesterService() {
        return semesterService;
    }

    public StudyPlanService getStudyPlanService() {
        return studyPlanService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UvService getUvService() {
        return uvService;
    }

    public UvUserService getUvUserService() {
        return uvUserService;
    }
}
