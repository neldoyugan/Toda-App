package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toda.R;
import com.example.toda.adapter.Booking;
import com.example.toda.adapter.PassengerBookingHistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerBookingHistory;
    PassengerBookingHistoryAdapter adapter;
    List<Booking> bookingList;

    LinearLayout emptyState;
    TextView tvRideCount;

    FirebaseAuth mAuth;
    DatabaseReference bookingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history_activity);

        recyclerBookingHistory = findViewById(R.id.recyclerBookingHistory);
        emptyState             = findViewById(R.id.emptyState);
        tvRideCount            = findViewById(R.id.tvRideCount);

        bookingList = new ArrayList<>();
        adapter     = new PassengerBookingHistoryAdapter(bookingList);
        recyclerBookingHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerBookingHistory.setAdapter(adapter);

        // Back button
        View ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackPressed());

        // Auth check
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Load only this passenger's bookings from Firebase
        bookingRef = FirebaseDatabase.getInstance().getReference("Bookings");
        bookingRef.orderByChild("passengerId").equalTo(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        bookingList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String pickup    = ds.child("pickup").getValue(String.class);
                            String dropoff   = ds.child("dropoff").getValue(String.class);
                            String date      = ds.child("date").getValue(String.class);
                            String time      = ds.child("time").getValue(String.class);
                            String fare      = ds.child("fare").getValue(String.class);
                            String status    = ds.child("status").getValue(String.class);
                            String passengers = ds.child("passengers").getValue(String.class);
                            Long   timestamp = ds.child("timestamp").getValue(Long.class);

                            String dateTime  = (date != null ? date : "?") + "  ·  " + (time != null ? time : "?");
                            bookingList.add(new Booking(
                                    pickup    != null ? pickup    : "—",
                                    dropoff   != null ? dropoff   : "—",
                                    dateTime,
                                    fare      != null ? fare      : "₱0.00",
                                    status    != null ? status    : "Pending",
                                    timestamp != null ? timestamp : 0L
                            ));
                        }

                        // Sort newest first
                        Collections.sort(bookingList, (a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));

                        adapter.notifyDataSetChanged();

                        // Show/hide empty state
                        if (bookingList.isEmpty()) {
                            emptyState.setVisibility(View.VISIBLE);
                            recyclerBookingHistory.setVisibility(View.GONE);
                            tvRideCount.setText("0 rides");
                        } else {
                            emptyState.setVisibility(View.GONE);
                            recyclerBookingHistory.setVisibility(View.VISIBLE);
                            tvRideCount.setText(bookingList.size() + " ride" + (bookingList.size() == 1 ? "" : "s"));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
    }
}
