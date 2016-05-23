package com.advisorapp.api.dao;

import com.advisorapp.api.model.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
public interface OptionRepository extends PagingAndSortingRepository<Option, Long> {
}