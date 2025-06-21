package com.example.pazeeraiScheduler.dto;

import java.util.List;

public class ScheduleRequest {
    private List<String> skippers;

    public List<String> getSkippers() {
        return skippers;
    }

    public void setSkippers(List<String> skippers) {
        this.skippers = skippers;
    }
}