package com.example.pump.FachLogic.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {
    private String name;
    private final int ID;
    private static int id_counter = 0;
    private ArrayList<Exercise> exercises;

    public Workout(String name){
        this.name = name;
        ID = id_counter++;
        this.exercises = null;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Workout{name='" + name + "'}";
    }
}
