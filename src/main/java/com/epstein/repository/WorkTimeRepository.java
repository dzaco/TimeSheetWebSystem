package com.epstein.repository;

import com.epstein.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer> {

    @Query(value = "select * from work_times where date = :date and user_id = :user_id", nativeQuery = true)
    Optional<WorkTime> findByDateAndUserId(LocalDate date, Integer user_id);

}
