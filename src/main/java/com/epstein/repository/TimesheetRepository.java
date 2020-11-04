package com.epstein.repository;

import com.epstein.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet,Integer> {
    @Query(value = "SELECT * FROM timesheets t WHERE t.user_id = :id ORDER BY t.year desc, t.month desc", nativeQuery = true)
    List<Timesheet> findByUserId(int id);

    @Query(value = "SELECT count(*) FROM timesheets t " +
            "WHERE t.user_id = :userId " +
            "AND t.project_id = :projectId " +
            "AND t.stage = :stage " +
            "AND t.month = :month " +
            "AND t.year = :year",
            nativeQuery = true)
    Integer findAllWhere(int userId, int projectId, int stage, int month, int year);

    @Query(value = "SELECT * FROM db_epstein.timesheets WHERE project_id = :id" ,
            nativeQuery = true)
    List<Timesheet> findByProjectId(int id);

    @Query(value = "select * from timesheets where user_id = :userId and year = :year and month = :month", nativeQuery = true)
    Optional<Timesheet> findByUserIdAndDate(Integer userId,Integer year, Integer month);
}
