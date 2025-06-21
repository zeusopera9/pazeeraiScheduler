package com.example.pazeeraiScheduler.controller;

import com.example.pazeeraiScheduler.dto.ScheduleRequest;
import com.example.pazeeraiScheduler.service.PazeeraiSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PazeeraiSchedulerController {

    @Autowired
    private PazeeraiSchedulerService schedulerService;

    @PostMapping("/init")
    public ResponseEntity<String> initAttendees(@RequestBody List<String> names) {
        schedulerService.initializeAttendees(names);
        return ResponseEntity.ok("Attendees initialized.");
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> schedule(@RequestBody ScheduleRequest request) {
        String assigned = schedulerService.scheduleWeek(request.getSkippers());
        return ResponseEntity.ok("Assigned: " + assigned);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> summary() {
        return ResponseEntity.ok(schedulerService.getSummary());
    }
}
