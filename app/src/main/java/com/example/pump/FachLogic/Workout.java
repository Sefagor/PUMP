package com.example.pump.FachLogic;

public class Workout {
    private String name;
    private String details;

    public Workout(String name, String details){
        this.name = name;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
