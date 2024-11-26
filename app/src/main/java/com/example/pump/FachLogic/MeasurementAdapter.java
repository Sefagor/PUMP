package com.example.pump.FachLogic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.MeasurementType;
import com.example.pump.R;

import java.util.ArrayList;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
    private ArrayList<Measurements> measurements = new ArrayList<>();
    private MeasurementType measurementType;
    private MeasurementAdapter.OnItemClickListener listener;

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
        holder.measurementValue.setText(String.format("%.2f cm", measurements.get(position).getNumberData()));
        holder.date.setText(measurements.get(position).getDate());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

