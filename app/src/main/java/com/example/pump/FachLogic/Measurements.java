package com.example.pump.FachLogic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Measurements {

    private float numberData;
    private String date;

    public Measurements(float numberData) {
        this.numberData= numberData;
    }

    public Measurements(float numberData, String date) {
        this.numberData= numberData;
        this.date = date;
    }

    public float getNumberData() {
        return numberData;
    }

    public String getDate() {
        return date;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
