package com.example.pazeeraiScheduler.service;

import com.example.pazeeraiScheduler.entity.Attendee;
import com.example.pazeeraiScheduler.entity.WeekLog;
import com.example.pazeeraiScheduler.repository.AttendeeRepository;
import com.example.pazeeraiScheduler.repository.WeekLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PazeeraiSchedulerService {

    @Autowired
    private AttendeeRepository attendeeRepo;

    @Autowired
    private WeekLogRepository weekLogRepo;

    public String scheduleWeek(List<String> unavailable) {
        List<Attendee> attendees = attendeeRepo.findAll();
        Queue<Attendee> queue = new LinkedList<>(attendees);
        int week = (int) weekLogRepo.count() + 1;

        int minDuties = attendees.stream()
                .mapToInt(Attendee::getResponsibilityCount)
                .min().orElse(0);

        Attendee assigned = null;

        while (!queue.isEmpty()) {
            Attendee person = queue.poll();
            if (unavailable.contains(person.getName())) {
                queue.offer(person);
            } else if (person.getResponsibilityCount() == minDuties) {
                person.setResponsibilityCount(person.getResponsibilityCount() + 1);
                assigned = person;
                attendeeRepo.save(person);
                break;
            } else {
                queue.offer(person);
            }
        }

        if (assigned == null) {
            throw new RuntimeException("No available attendee to assign this week.");
        }

        weekLogRepo.save(new WeekLog(week, unavailable, assigned.getName()));
        return assigned.getName();
    }

    public Map<String, Object> getSummary() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("responsibilityCount", attendeeRepo.findAll());
        map.put("skipLog", weekLogRepo.findAll());
        return map;
    }

    public String getFormattedSummary() {
        List<Attendee> attendees = attendeeRepo.findAll();
//        List<WeekLog> logs = weekLogRepo.findAll();
        List<WeekLog> logs = weekLogRepo.findAllWithSkipped();


        StringBuilder sb = new StringBuilder("📋 **Summary:**\n");

        for (Attendee attendee : attendees) {
            sb.append("- ")
                    .append(attendee.getName())
                    .append(": ")
                    .append(attendee.getResponsibilityCount())
                    .append(" week(s)\n");
        }

        if (!logs.isEmpty()) {
            WeekLog latestLog = logs.get(logs.size() - 1);
            List<String> skippers = latestLog.getSkipped();  // ✅ Corrected field name

            if (skippers != null && !skippers.isEmpty()) {
                sb.append("\n⏭️ **Skipped This Week:** ")
                        .append(String.join(", ", skippers));
            }
        }

        return sb.toString();
    }


    public void initializeAttendees(List<String> names) {
        for (String name : names) {
            if (!attendeeRepo.existsById(name)) {
                attendeeRepo.save(new Attendee(name));
            }
        }
    }
}
