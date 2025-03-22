package com.example.pump.FachLogic.Classes;

import com.example.pump.FachLogic.Enums.ExerciseKategorie;
import com.example.pump.FachLogic.Enums.MeasurementType;

import java.util.List;

public class Exercise {
    private String name;
    private List<String> primaryMuscles;
    private String muscle;
    private String description;
    private final int id;
    private static int idCounter = 0;

    public Exercise(String name, String muscle, String description) {
        this.name = name;
        this.muscle = muscle;
        this.description = description;
        this.id = idCounter++;
    }

    public String getName() {
        return name;
    }

    public List<String> getPrimaryMuscles() {
        return primaryMuscles;
    }

    public String getFirstMuscle() {
        return muscle;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}

