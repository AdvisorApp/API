package com.advisorapp.api.dao;

import com.advisorapp.api.model.User;
import com.advisorapp.api.model.UvUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UvUserRepository extends PagingAndSortingRepository<UvUser,Long> {

}
