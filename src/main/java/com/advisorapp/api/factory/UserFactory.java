package com.advisorapp.api.factory;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
            String password

    ) {
        User user = new User();

        return this.createUser(
                user
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setBirthday(birthday)
                        .setRemoteId(remoteId)
                        .setPassword(password)
        );
    }

    public User createUser(User user) {
        user = this.userService.signUp(user);

        // Create All UvUser for each UVs existing on database.
        for (Uv uv : this.uvRepository.findAll()) {
            UvUser uvUser = new UvUser();
            this.uvUserFactory.getUvUserService().createUvUser(uvUser.setUser(user).setUv(uv));
        }

        return user;
    }

    public UserService getUserService() {
        return userService;
    }

    public UvUserFactory getUvUserFactory() {
        return this.uvUserFactory;
    }
}
