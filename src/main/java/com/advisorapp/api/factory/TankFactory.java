package com.advisorapp.api.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TankFactory {

    @Autowired
    private SemesterFactory semesterFactory;

    @Autowired
    private StudyPlanFactory studyPlanFactory;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private UvFactory uvFactory;

    @Autowired
    private UvTypeFactory uvTypeFactory;

    @Autowired
    private UvUserFactory uvUserFactory;

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public StudyPlanFactory getStudyPlanFactory() {
        return studyPlanFactory;
    }

    public SemesterFactory getSemesterFactory() {
        return semesterFactory;
    }

    public UvFactory getUvFactory() {
        return uvFactory;
    }

    public UvTypeFactory getUvTypeFactory() {
        return uvTypeFactory;
    }

    public UvUserFactory getUvUserFactory() {
        return uvUserFactory;
    }
}
