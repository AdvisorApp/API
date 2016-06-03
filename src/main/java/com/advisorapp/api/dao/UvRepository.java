package com.advisorapp.api.dao;

import com.advisorapp.api.model.Option;
import com.advisorapp.api.model.Uv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by Steeve on 20/05/2016.
 */
public interface UvRepository extends PagingAndSortingRepository<Uv,Long> {
    Set<Uv> findByMinSemesterAndOption(int minSemester, Option option);

    @Query("SELECT u FROM Uv u WHERE u.option IS NULL and u.minSemester = :minSemester")
    Set<Uv> findByMinSemesterAndWithoutOption(@Param("minSemester") int minSemester);

    @Query("SELECT u FROM Uv u WHERE u.isAvailableForCart = true")
    Set<Uv> findByAvailableForCart();

}
