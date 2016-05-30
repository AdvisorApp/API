package com.advisorapp.api.factory;

import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserFactory {
    @Autowired
    private UserService userService;

    public User createUser(
            String firstName,
            String lastName,
            String email,
            Date birthday,
            String remoteId,
            Set<StudyPlan> studyPlans

    ) {
        User user = new User();

        return this.userService.createUser(
            user
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setBirthday(birthday)
                .setRemoteId(remoteId)
                .setStudyPlans(studyPlans == null ? new HashSet<StudyPlan>() : studyPlans)
        );
    }
}
