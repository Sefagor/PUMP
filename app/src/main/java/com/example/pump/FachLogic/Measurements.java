package com.example.pump.FachLogic;

import com.example.pump.MeasurementType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Measurements {

    private float numberData = 0;
    private String date = "0000-00-00";


    public Measurements(float numberData) {
        this.numberData= numberData;
        this.date = getCurrentDate();
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
