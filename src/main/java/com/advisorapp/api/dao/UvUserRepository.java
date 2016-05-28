package com.advisorapp.api.dao;

import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UvUserRepository extends PagingAndSortingRepository<UvUser,Long> {

    @Query("select u from UvUser u where u.user = :user")
    Set<UvUser> findByUser(@Param("user") User user);

    @Query("select u from UvUser u where u.uv = :uv")
    Set<UvUser> findByUV(@Param("uv") Uv uv);

    @Query("select u from UvUser u where u.uv = :uv or u.user = :user")
    UvUser findByUvAndUser(@Param("uv") Uv uv,
                           @Param("user") User user);

}
