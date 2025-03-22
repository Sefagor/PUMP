package com.example.pump.FachLogic.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class BodyParts extends AppCompatActivity {
    private LinearLayout neckTxt, shoulderTxt, chestTxt, armsTxt, waistTxt, hipsTxt, legsTxt;
    private TextView neckValueTxt, shoulderValueTxt, chestValueTxt, armsValueTxt, waistValueTxt, hipsValueTxt, legsValueTxt, pageTitle;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.body_parts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bodyPartsMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(BodyParts.this, MeasurementsActivity.class));
                return true;
            }
            if (item.getItemId() == R.id.nav_workout) {
                startActivity(new Intent(BodyParts.this, WorkoutActivity.class));
                return true;
            }
            return false;
        });

        initViews();
        setTxtValue();

        neckTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neckIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.NECK);
                startActivity(neckIntent);
            }
        });
        shoulderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoulderIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.SHOULDER);
                startActivity(shoulderIntent);
            }
        });
        chestTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chestIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.CHEST);
                startActivity(chestIntent);
            }
        });
        armsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent armsIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.ARMS);
                startActivity(armsIntent);
            }
        });
        waistTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent waistIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.WAIST);
                startActivity(waistIntent);
            }
        });
        hipsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hipsIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.HIPS);
                startActivity(hipsIntent);
            }
        });
        legsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent legsIntent = BodyPartActivity.createIntent(BodyParts.this, MeasurementType.LEGS);
                startActivity(legsIntent);
            }
        });
    }

    private TextView txtType(MeasurementType type){
        switch (type){
            case NECK: return neckValueTxt;
            case SHOULDER: return shoulderValueTxt;
            case CHEST: return chestValueTxt;
            case ARMS: return armsValueTxt;
            case WAIST: return waistValueTxt;
            case HIPS: return hipsValueTxt;
            case LEGS: return legsValueTxt;
            default:throw new IllegalArgumentException("Unknown measurement type: " + type);
        }
    }

    @SuppressLint("DefaultLocale")
    private void setTxt(MeasurementType type){
        ArrayList<Measurements> measurements = Utils.getInstance(this).getMeasurements(type);
        txtType(type).setText(String.format("%.2f cm",measurements.size()-1>=0 ? measurements.get(measurements.size()-1).getNumberData(): 0 ));
    }

    private void setTxtValue(){
        setTxt(MeasurementType.NECK);
        setTxt(MeasurementType.SHOULDER);
        setTxt(MeasurementType.CHEST);
        setTxt(MeasurementType.ARMS);
        setTxt(MeasurementType.WAIST);
        setTxt(MeasurementType.HIPS);
        setTxt(MeasurementType.LEGS);
    }

    private void initViews() {
        neckTxt = findViewById(R.id.neck);
        shoulderTxt = findViewById(R.id.shoulder);
        chestTxt = findViewById(R.id.chest);
        armsTxt = findViewById(R.id.arms);
        waistTxt = findViewById(R.id.waist);
        hipsTxt = findViewById(R.id.hips);
        legsTxt = findViewById(R.id.legs);
        neckValueTxt = findViewById(R.id.neckValue);
        shoulderValueTxt = findViewById(R.id.shoulderValue);
        chestValueTxt = findViewById(R.id.chestValue);
        armsValueTxt = findViewById(R.id.armsValue);
        waistValueTxt = findViewById(R.id.waistValue);
        hipsValueTxt = findViewById(R.id.hipsValue);
        legsValueTxt = findViewById(R.id.legsValue);
        pageTitle = findViewById(R.id.measurementPagetxt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MeasurementsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Call this to ensure the current activity is finished
    }
}

