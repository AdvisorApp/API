package com.advisorapp.api.service;

import com.advisorapp.api.dao.UvUserRepository;
import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class UvUserService {
    @Autowired
    private UvUserRepository uvUserRepository;

    public UvUser createUvUser(UvUser uvUser) {
        return uvUserRepository.save(uvUser);
    }

    public UvUser getUvUser(long id) {
        return uvUserRepository.findOne(id);
    }

    public Set<UvUser> getUvUserByUser(User user) {
        return uvUserRepository.findByUser(user);
    }

    public Set<UvUser> getUvUserByUV(Uv uv) {
        return uvUserRepository.findByUV(uv);
    }

    public UvUser getUvUserByUVAndUser(Uv uv, User user) {
        return uvUserRepository.findByUvAndUser(uv, user);
    }

    public void updateUvUser(UvUser uvUser) {
        uvUserRepository.save(uvUser);
    }

    public void deleteUvUser(Long id) {
        uvUserRepository.delete(id);
    }

    public Set<UvUser> getAllUvUsers() {
        return this.uvUserRepository.findAll();
    }
}
