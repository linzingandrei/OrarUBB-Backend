package com.example.OrarUBB_Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.OrarUBB_Backend.domain.Schedule;

import jakarta.transaction.Transactional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = """
        SELECT *
        FROM schedules s
        WHERE s.user_id = :userId
    """, nativeQuery = true)
    List<Schedule> getAllSchedulesForCertainUser(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE
        FROM schedules s
        WHERE s.user_id = :userId 
    """, nativeQuery = true)
    int deleteAllSchedulesForCurrentUser(@Param("userId") String userId);

    // @Query(value = """
        // INSERT INTO schedules ()
        // VALUES ()    
    // """, nativeQuery = true)
    // List<Schedule> insertSchedule
}
