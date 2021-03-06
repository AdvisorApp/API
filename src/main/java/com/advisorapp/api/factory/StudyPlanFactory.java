package com.advisorapp.api.factory;

import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.StudyPlanService;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudyPlanFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private SemesterFactory semesterFactory;

    public StudyPlan createStudyPlan(
            String name,
            Set<Semester> semesters,
            User user
    ) {
        StudyPlan studyPlan = new StudyPlan();

        this.studyPlanService.createStudyPlan(
            studyPlan
                .setName(name)
                .setSemesters(semesters == null ? new HashSet<Semester>() : semesters)
        );

        return this.createStudyPlanWithUser(studyPlan, user);
    }

    public StudyPlan createStudyPlanWithUser(StudyPlan studyPlan, User user)
    {
        StudyPlan SP = this.studyPlanService.createStudyPlan(studyPlan);

        if (user != null) {
            this.userService.updateUser(user.addStudyPlan(SP));
        }

        return SP;
    }

    public void createDefaultStudyPlanForUser(User user) {
        String userKey = (user != null ? user.getFirstName() + " " : "");
        this.semesterFactory.initDefaultSemesters(this.createStudyPlan(userKey +  "Default Study Plan", null, user));
    }

    public StudyPlanService getStudyPlanService()
    {
        return studyPlanService;
    }
}
