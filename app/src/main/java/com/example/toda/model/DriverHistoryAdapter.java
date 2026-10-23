package com.example.toda.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toda.R;

import java.util.List;

public class DriverHistoryAdapter extends RecyclerView.Adapter<DriverHistoryAdapter.ViewHolder> {

    List<TripHistory> historyList;

    public DriverHistoryAdapter(List<TripHistory> historyList) {
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_driver_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TripHistory history = historyList.get(position);
        holder.tvDestination.setText(history.getDestination());
        holder.tvFare.setText("Fare: " + history.getFare());
        holder.tvDate.setText(history.getDate());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDestination, tvFare, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvFare = itemView.findViewById(R.id.tvFare);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
