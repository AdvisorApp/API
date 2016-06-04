package com.advisorapp.api.dao;

import com.advisorapp.api.model.User;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UvUserRepository extends PagingAndSortingRepository<UvUser,Long> {

    @Query("select u from UvUser u where u.user = :user")
    Set<UvUser> findByUser(@Param("user") User user);

    @Query("select u from UvUser u where u.uv = :uv")
    Set<UvUser> findByUV(@Param("uv") Uv uv);

    @Query("select u from UvUser u where u.uv = :uv and u.user = :user")
    UvUser findByUvAndUser(@Param("uv") Uv uv,
                           @Param("user") User user);

    Set<UvUser> findAll();
}
