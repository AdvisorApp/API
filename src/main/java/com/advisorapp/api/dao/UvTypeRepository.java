package com.advisorapp.api.dao;

import com.advisorapp.api.model.UvType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UvTypeRepository extends PagingAndSortingRepository<UvType,Long> {
    public Set<UvType> findAll();
}
