package com.example.pump.FachLogic.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;

import java.util.ArrayList;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
    private ArrayList<Measurements> measurements = new ArrayList<>();
    private MeasurementType measurementType;
    private OnItemClickListener listener;

    public MeasurementAdapter(MeasurementType type) {
        this.measurementType = type;
    }

    public void setMeasurements(ArrayList<Measurements> measurements) {
        this.measurements = measurements;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurements measurement = measurements.get(position);

        String unit;
        if (measurementType == null) {
            unit = "cm";
        } else {
            switch (measurementType) {
                case WEIGHT:
                    unit = "kg";
                    break;
                case CALORIES:
                    unit = "kcal";
                    break;
                default:
                    unit = "cm";
                    break;
            }
        }

        holder.measurementValue.setText(String.format("%.2f %s", measurement.getNumberData(), unit));
        holder.date.setText(measurement.getDate());
    }


    @Override
    public int getItemCount() {
        return measurements.size();
    }

    public void removeItem(int positon){
        measurements.remove(positon);
        notifyItemRemoved(positon);
        notifyItemRangeChanged(positon, measurements.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, measurementValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            measurementValue = itemView.findViewById(R.id.itemValue);
            itemView.setOnClickListener(view -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}

