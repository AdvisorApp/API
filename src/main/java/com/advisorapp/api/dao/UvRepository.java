package com.advisorapp.api.dao;

import com.advisorapp.api.model.Uv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UvRepository extends PagingAndSortingRepository<Uv,Long> {
    @Query("SELECT u FROM Uv u WHERE u.isAvailableForCart = true")
    Set<Uv> findByAvailableForCart();

    Uv findByRemoteId(String remoteId);


    Set<Uv> findByRemoteIdIn(Collection<String> remoteId);
}
