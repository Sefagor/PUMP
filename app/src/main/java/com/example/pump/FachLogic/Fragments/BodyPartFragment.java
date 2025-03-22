package com.example.pump.FachLogic.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.DialogUtils;
import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.MeasurementAdapter;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;

import java.util.ArrayList;

public class BodyPartFragment extends Fragment {
    private static final String EXTRA_BODY_PART = "extra_body_part";
    private RecyclerView measurementList;
    private MeasurementAdapter measurementAdapter;
    private ArrayList<Measurements> measurements;
    private static BodyPartFragment instance;

    public static BodyPartFragment newInstance(MeasurementType type) {
        BodyPartFragment fragment = new BodyPartFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BODY_PART, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_measurements, container, false);

        TextView pageTitle = view.findViewById(R.id.measurementPagetxt);

        MeasurementType type = (MeasurementType) getArguments().getSerializable(EXTRA_BODY_PART);

        if (pageTitle != null) {
            setPageTitle(getContext(), type, pageTitle);
        }

        measurements = Utils.getInstance(getContext()).getMeasurements(type);

        initViews(view, type);


        return view;
    }

    public static BodyPartFragment getInstance() {
        if (instance == null) {
            instance = new BodyPartFragment();
        }
        return instance;
    }

    public void setPageTitle(Context context, MeasurementType type, TextView pageTitle) {
        if (pageTitle == null) {
            throw new NullPointerException("TextView is null");
        }
        switch (type) {
            case NECK: pageTitle.setText(context.getString(R.string.neck)); break;
            case SHOULDER: pageTitle.setText(context.getString(R.string.shoulder)); break;
            case CHEST: pageTitle.setText(context.getString(R.string.chest)); break;
            case ARMS: pageTitle.setText(context.getString(R.string.arms)); break;
            case WAIST: pageTitle.setText(context.getString(R.string.waist)); break;
            case HIPS: pageTitle.setText(context.getString(R.string.hips)); break;
            case LEGS: pageTitle.setText(context.getString(R.string.legs)); break;
            case CALORIES: pageTitle.setText(context.getString(R.string.calorie)); break;
            case WEIGHT: pageTitle.setText(context.getString(R.string.weight)); break;
            case HEIGHT: pageTitle.setText(context.getString(R.string.height)); break;
            default: throw new IllegalArgumentException("Unknown MeasurementType: " + type);
        }
    }

    private void initViews(View view, MeasurementType type) {
        measurementList = view.findViewById(R.id.measurementList);
        measurementAdapter = new MeasurementAdapter(type);
        measurementAdapter.setMeasurements(measurements);
        measurementList.setAdapter(measurementAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        measurementList.setLayoutManager(layoutManager);

        ImageButton addButton = view.findViewById(R.id.addBtn);
        addButton.setOnClickListener(v -> showAddMeasurementDialog(type));

        measurementAdapter.setOnItemClickListener(position -> showUpdateMeasurementDialog(type, position));
    }

    private void showAddMeasurementDialog(MeasurementType type) {
        DialogUtils.showAddMeasurementDialog(getContext(), measurements, measurementAdapter,
                R.layout.add_entry, R.id.entry,
                R.id.buttonSubmit, type);
    }

    private void showUpdateMeasurementDialog(MeasurementType type, int position) {
        DialogUtils.showUpdateMeasurementDialog(getContext(), measurements, measurementAdapter,
                position, R.layout.update_entry, R.id.removeEntryButton, type);
    }

}

