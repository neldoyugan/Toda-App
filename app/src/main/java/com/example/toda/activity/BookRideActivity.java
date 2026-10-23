package com.example.toda.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class BookRideActivity extends AppCompatActivity {

    private EditText etPickup, etDropoff, etDate, etTime;
    private Spinner spinnerPassengers;
    private TextView tvEstimatedFare;
    private Button btnBookNow;

    // Base fare: ₱25 for 1 pax, +₱10 per extra passenger
    private static final double BASE_FARE = 25.0;
    private static final double EXTRA_PER_PAX = 10.0;

    FirebaseAuth mAuth;
    DatabaseReference bookingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_ride_activity);

        etPickup  = findViewById(R.id.etPickup);
        etDropoff = findViewById(R.id.etDropoff);
        etDate    = findViewById(R.id.etDate);
        etTime    = findViewById(R.id.etTime);
        spinnerPassengers = findViewById(R.id.spinnerPassengers);
        tvEstimatedFare   = findViewById(R.id.tvEstimatedFare);
        btnBookNow        = findViewById(R.id.btnBookNow);

        mAuth      = FirebaseAuth.getInstance();
        bookingRef = FirebaseDatabase.getInstance().getReference("Bookings");

        // Animate logo
        ImageView imgLogo = findViewById(R.id.imgLogo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgLogo.startAnimation(fadeIn);

        // Back button
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackPressed());

        // Spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.passenger_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPassengers.setAdapter(adapter);

        // Date picker
        etDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) ->
                    etDate.setText(String.format("%02d/%02d/%d", day, month + 1, year)),
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Time picker
        etTime.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                String period = hour >= 12 ? "PM" : "AM";
                int displayHour = hour % 12;
                if (displayHour == 0) displayHour = 12;
                etTime.setText(String.format("%d:%02d %s", displayHour, minute, period));
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
        });

        // Estimate fare — position 0 = 1 passenger
        spinnerPassengers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                int passengers = position + 1;
                double fare = BASE_FARE + (EXTRA_PER_PAX * (passengers - 1));
                tvEstimatedFare.setText("₱" + String.format("%.2f", fare));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Book Now — validate and save to Firebase
        btnBookNow.setOnClickListener(v -> bookRide());
    }

    private void bookRide() {
        String pickup     = etPickup.getText().toString().trim();
        String dropoff    = etDropoff.getText().toString().trim();
        String date       = etDate.getText().toString().trim();
        String time       = etTime.getText().toString().trim();
        String fare       = tvEstimatedFare.getText().toString().trim();
        String passengers = spinnerPassengers.getSelectedItem().toString();

        if (TextUtils.isEmpty(pickup)) {
            etPickup.setError("Pickup location is required");
            etPickup.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dropoff)) {
            etDropoff.setError("Drop-off location is required");
            etDropoff.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button to prevent double-submit
        btnBookNow.setEnabled(false);
        btnBookNow.setText("Booking...");

        String passengerId = mAuth.getCurrentUser().getUid();
        String bookingId   = bookingRef.push().getKey();

        HashMap<String, Object> booking = new HashMap<>();
        booking.put("passengerId", passengerId);
        booking.put("pickup",      pickup);
        booking.put("dropoff",     dropoff);
        booking.put("date",        date);
        booking.put("time",        time);
        booking.put("passengers",  passengers);
        booking.put("fare",        fare);
        booking.put("status",      "Pending");
        booking.put("timestamp",   System.currentTimeMillis());

        if (bookingId != null) {
            bookingRef.child(bookingId).setValue(booking)
                    .addOnSuccessListener(aVoid -> {
                        Intent intent = new Intent(BookRideActivity.this, BookingConfirmActivity.class);
                        intent.putExtra("pickup",     pickup);
                        intent.putExtra("dropoff",    dropoff);
                        intent.putExtra("date",       date);
                        intent.putExtra("time",       time);
                        intent.putExtra("passengers", passengers);
                        intent.putExtra("fare",       fare);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        btnBookNow.setEnabled(true);
                        btnBookNow.setText("Book Ride Now  🛵");
                        Toast.makeText(this, "Booking failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        }
    }
}
