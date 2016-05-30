package com.advisorapp.api.dao;

import com.advisorapp.api.model.Semester;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface SemesterRepository extends PagingAndSortingRepository<Semester,Long> {

}
