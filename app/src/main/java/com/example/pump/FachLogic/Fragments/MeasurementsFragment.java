package com.example.pump.FachLogic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.Data.Utils;
import com.example.pump.R;

import java.util.ArrayList;

public class MeasurementsFragment extends Fragment {

    private LinearLayout weight, calorie, fat, bodyParts, height;
    private TextView heightTxt, fatValue, heightValueTxt, weightValue, calorieValue, pageTitle;
    private ArrayList<Measurements> heights;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measurements_base, container, false);
        initViews(view);
        setupListeners();
        loadData();
        return view;
    }

    private void initViews(View view) {
        weight = view.findViewById(R.id.weight);
        weightValue = view.findViewById(R.id.weightValue);
        calorie = view.findViewById(R.id.calorie);
        calorieValue = view.findViewById(R.id.calorieValue);
        bodyParts = view.findViewById(R.id.bodyParts);
        fat = view.findViewById(R.id.fat);
        fatValue = view.findViewById(R.id.fatValue);
        height = view.findViewById(R.id.height);
        heightTxt = view.findViewById(R.id.heightTxt);
        heightValueTxt = view.findViewById(R.id.heightValue);
        pageTitle = view.findViewById(R.id.measurementPagetxt);
        heights = new ArrayList<>();
    }

    private void setupListeners() {
        height.setOnClickListener(v -> navigateToFragment(BodyPartFragment.newInstance(MeasurementType.HEIGHT)));
        weight.setOnClickListener(v -> navigateToFragment(BodyPartFragment.newInstance(MeasurementType.WEIGHT)));
        calorie.setOnClickListener(v -> navigateToFragment(BodyPartFragment.newInstance(MeasurementType.CALORIES)));
        bodyParts.setOnClickListener(v -> navigateToFragment(new AllBodyPartsFragment()));
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }



    @SuppressWarnings("SetTextI18n")
    private void loadData() {
        heightValueTxt.setText(formatMeasurement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.HEIGHT), "cm"));
        weightValue.setText(formatMeasurement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.WEIGHT), "kg"));
        calorieValue.setText(formatMeasurement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.CALORIES), "kcal"));
        fatValue.setText(String.format("%.2f%%", bodyFatCalculator()));
    }

    private String formatMeasurement(ArrayList<Measurements> measurements, String unit) {
        Measurements last = getLastElement(measurements);
        return last != null ? String.format("%.2f %s", last.getNumberData(), unit) : "0";
    }

    public static Measurements getLastElement(ArrayList<Measurements> list) {
        return list.isEmpty() ? new Measurements(0f) : list.get(list.size() - 1);
    }

    private double bodyFatCalculator() {
        float neck = getLastElement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.NECK)).getNumberData();
        float waist = getLastElement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.WAIST)).getNumberData();
        float height = getLastElement(Utils.getInstance(requireContext()).getMeasurements(MeasurementType.HEIGHT)).getNumberData();
        return (86.010 * Math.log10(waist - neck)) - (70.041 * Math.log10(height)) + 30.30;
    }
}
