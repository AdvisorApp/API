package com.advisorapp.api.factory;

import com.advisorapp.api.model.Option;
import com.advisorapp.api.model.Semester;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.StudyPlanService;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudyPlanFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private StudyPlanService studyPlanService;

    public StudyPlan createStudyPlanWithUser(
            User user,
            String name,
            Set<Semester> semesters,
            Option option
    ) {
        StudyPlan studyPlan = new StudyPlan();

        this.userService.updateUser(
            user.addStudyPlan(
                studyPlan
                    .setName(name)
                    .setSemesters(semesters)
                    .setOption(option)
            )
        );

        return studyPlan;
    }

    public StudyPlan createStudyPlanWithoutUser(
            String name,
            Set<Semester> semesters,
            Option option
    ) {
        StudyPlan studyPlan = new StudyPlan();

        this.studyPlanService.updateStudyPlan(
            studyPlan
                .setName(name)
                .setSemesters(semesters)
                .setOption(option)
        );

        return studyPlan;
    }
}
