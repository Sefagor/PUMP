package com.example.pump.FachLogic.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Activitys.BodyPartActivity;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AllBodyPartsFragment extends Fragment {
    private LinearLayout neckTxt, shoulderTxt, chestTxt, armsTxt, waistTxt, hipsTxt, legsTxt;
    private TextView neckValueTxt, shoulderValueTxt, chestValueTxt, armsValueTxt, waistValueTxt, hipsValueTxt, legsValueTxt, pageTitle;

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.body_parts, container, false);

        initViews(rootView);
        setTxtValue();

        neckTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.NECK)));
        shoulderTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.SHOULDER)));
        chestTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.CHEST)));
        armsTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.ARMS)));
        waistTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.WAIST)));
        hipsTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.HIPS)));
        legsTxt.setOnClickListener(view -> navigateToBodyPartFragment(BodyPartFragment.newInstance(MeasurementType.LEGS)));

        return rootView;
    }

    private void navigateToBodyPartFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private TextView txtType(MeasurementType type) {
        switch (type) {
            case NECK: return neckValueTxt;
            case SHOULDER: return shoulderValueTxt;
            case CHEST: return chestValueTxt;
            case ARMS: return armsValueTxt;
            case WAIST: return waistValueTxt;
            case HIPS: return hipsValueTxt;
            case LEGS: return legsValueTxt;
            default: throw new IllegalArgumentException("Unknown measurement type: " + type);
        }
    }

    @SuppressLint("DefaultLocale")
    private void setTxt(MeasurementType type) {
        ArrayList<Measurements> measurements = Utils.getInstance(getActivity()).getMeasurements(type);
        txtType(type).setText(String.format("%.2f cm", measurements.size() - 1 >= 0 ? measurements.get(measurements.size() - 1).getNumberData() : 0));
    }

    private void setTxtValue() {
        setTxt(MeasurementType.NECK);
        setTxt(MeasurementType.SHOULDER);
        setTxt(MeasurementType.CHEST);
        setTxt(MeasurementType.ARMS);
        setTxt(MeasurementType.WAIST);
        setTxt(MeasurementType.HIPS);
        setTxt(MeasurementType.LEGS);
    }

    private void initViews(View rootView) {
        neckTxt = rootView.findViewById(R.id.neck);
        shoulderTxt = rootView.findViewById(R.id.shoulder);
        chestTxt = rootView.findViewById(R.id.chest);
        armsTxt = rootView.findViewById(R.id.arms);
        waistTxt = rootView.findViewById(R.id.waist);
        hipsTxt = rootView.findViewById(R.id.hips);
        legsTxt = rootView.findViewById(R.id.legs);
        neckValueTxt = rootView.findViewById(R.id.neckValue);
        shoulderValueTxt = rootView.findViewById(R.id.shoulderValue);
        chestValueTxt = rootView.findViewById(R.id.chestValue);
        armsValueTxt = rootView.findViewById(R.id.armsValue);
        waistValueTxt = rootView.findViewById(R.id.waistValue);
        hipsValueTxt = rootView.findViewById(R.id.hipsValue);
        legsValueTxt = rootView.findViewById(R.id.legsValue);
        pageTitle = rootView.findViewById(R.id.measurementPagetxt);
    }
}

