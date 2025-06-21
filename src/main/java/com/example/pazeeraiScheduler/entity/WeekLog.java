package com.example.pazeeraiScheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
public class WeekLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int week;

    @ElementCollection
    private List<String> skipped;

    private String assigned;

    public WeekLog() {}

    public WeekLog(int week, List<String> skipped, String assigned) {
        this.week = week;
        this.skipped = skipped;
        this.assigned = assigned;
    }

    public List<String> getSkipped() {
        return skipped;
    }

    // Getters and Setters
}