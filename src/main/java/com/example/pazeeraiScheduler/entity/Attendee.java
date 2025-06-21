package com.example.pazeeraiScheduler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Attendee {

    @Id
    private String name;

    private int responsibilityCount = 0;

    public Attendee() {}

    public Attendee(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getResponsibilityCount() { return responsibilityCount; }
    public void setResponsibilityCount(int count) { this.responsibilityCount = count; }
}