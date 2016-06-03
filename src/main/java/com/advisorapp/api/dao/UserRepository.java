package com.advisorapp.api.dao;

import com.advisorapp.api.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.Set;
import java.util.Set;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserById(long id);

    User findUserByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    Set<User> findAll();
}
