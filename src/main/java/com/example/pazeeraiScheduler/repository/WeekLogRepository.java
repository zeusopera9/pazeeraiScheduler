package com.example.pazeeraiScheduler.repository;

import com.example.pazeeraiScheduler.entity.WeekLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeekLogRepository extends JpaRepository<WeekLog, Long> {

    // ✅ Fetch all week logs along with their skipped list
    @Query("SELECT wl FROM WeekLog wl LEFT JOIN FETCH wl.skipped")
    List<WeekLog> findAllWithSkipped();

    // ✅ Optional: Fetch only the latest week log if needed
    @Query("SELECT wl FROM WeekLog wl LEFT JOIN FETCH wl.skipped ORDER BY wl.week DESC")
    List<WeekLog> findTopWeekLogWithSkipped(); // you can get .get(0) from this
}
