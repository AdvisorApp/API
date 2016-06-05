package com.advisorapp.api.dao;

import com.advisorapp.api.model.StudyPlan;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * Created by Steeve on 23/05/2016.
 */
public interface StudyPlanRepository extends PagingAndSortingRepository<StudyPlan,Long> {
}
