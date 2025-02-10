package com.albertk.schoolmap.repository;

import com.albertk.schoolmap.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
        Set<School> findByCreatedById(Long userId);

}
