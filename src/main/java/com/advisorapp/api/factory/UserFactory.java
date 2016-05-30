package com.advisorapp.api.factory;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class UserFactory {
    @Autowired
    private UserService userService;

    @Autowired
    private UvUserFactory uvUserFactory;

    @Autowired
    private UvRepository uvRepository;

    public User createUser(
            String firstName,
            String lastName,
            String email,
            Date birthday,
            String remoteId,
            Set<StudyPlan> studyPlans

    ) {
        User user = new User();

        return this.createUser(
            user
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setBirthday(birthday)
                .setRemoteId(remoteId)
                .setStudyPlans(studyPlans == null ? new HashSet<StudyPlan>() : studyPlans)
        );
    }

    public User createUser(User user)
    {
        user = this.userService.createUser(user);

        // Create All UvUser for each UVs existing on database.
        Iterator<Uv> uvs = this.uvRepository.findAll().iterator();
        while(uvs.hasNext()){
            UvUser uvUser = new UvUser();
            uvUser.setUser(user);
            uvUser.setUv(uvs.next());
            this.uvUserFactory.getUvUserService().createUvUser(uvUser);
        }

        return user;
    }

    public UserService getUserService()
    {
        return userService;
    }

    public UvUserFactory getUvUserFactory()
    {
        return this.uvUserFactory;
    }
}
