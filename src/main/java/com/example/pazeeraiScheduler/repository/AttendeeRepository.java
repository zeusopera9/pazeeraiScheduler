package com.example.pazeeraiScheduler.repository;

import com.example.pazeeraiScheduler.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {}
