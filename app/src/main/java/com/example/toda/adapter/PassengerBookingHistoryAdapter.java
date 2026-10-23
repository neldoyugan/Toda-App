package com.example.toda.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toda.R;

import java.util.List;

public class PassengerBookingHistoryAdapter extends RecyclerView.Adapter<PassengerBookingHistoryAdapter.ViewHolder> {

    private List<Booking> bookingList;

    public PassengerBookingHistoryAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvPickup.setText(booking.getPickup());
        holder.tvDropoff.setText(booking.getDropoff());
        holder.tvDateTime.setText(booking.getDateTime());
        holder.tvFare.setText(booking.getFare());

        // Status chip color
        String status = booking.getStatus();
        holder.tvStatus.setText(status);
        switch (status) {
            case "Completed":
                holder.tvStatus.setBackgroundResource(R.drawable.btn_green);
                break;
            case "Accepted":
                holder.tvStatus.setBackgroundResource(R.drawable.btn_teal);
                break;
            case "Cancelled":
                holder.tvStatus.setBackgroundResource(R.drawable.red_button_box);
                break;
            default: // Pending
                holder.tvStatus.setBackgroundResource(R.drawable.btn_purple);
                break;
        }
    }

    @Override
    public int getItemCount() { return bookingList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPickup, tvDropoff, tvDateTime, tvFare, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPickup   = itemView.findViewById(R.id.tvPickup);
            tvDropoff  = itemView.findViewById(R.id.tvDropoff);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvFare     = itemView.findViewById(R.id.tvFare);
            tvStatus   = itemView.findViewById(R.id.tvStatus);
        }
    }
}
