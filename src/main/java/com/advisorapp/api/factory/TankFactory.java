package com.advisorapp.api.factory;

import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.model.UvUser;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

public class TankFactory {
    @Autowired
    private UserFactory userFactory;

    @Autowired
    private OptionFactory optionFactory;

    @Autowired
    private StudyPlanFactory studyPlanFactory;

    @Autowired
    private SemesterFactory semesterFactory;

    @Autowired
    private UvFactory uvFactory;

    @Autowired
    private UvTypeFactory uvTypeFactory;

    @Autowired
    private UvUser uvUser;

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public OptionFactory getOptionFactory() {
        return optionFactory;
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

    public UvUser getUvUser() {
        return uvUser;
    }
}
