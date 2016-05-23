package com.advisorapp.api.dao;

import com.advisorapp.api.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserById(long id);

}
