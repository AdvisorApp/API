package com.advisorapp.api.factory;

import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import com.advisorapp.api.service.UvUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UvUserFactory {
    @Autowired
    private UvUserService uvUserService;

    public UvUser create(
        User user,
        Uv uv,
        Double userAverage,
        Double uvMark,
        String uvComment
    ) {
        UvUser uvUser = new UvUser();

        return this.uvUserService.createUvUser(
            uvUser
                .setUser(user)
                .setUv(uv)
                .setUserAverage(userAverage)
                .setUvMark(uvMark)
                .setUvComment(uvComment)
        );
    }
}
