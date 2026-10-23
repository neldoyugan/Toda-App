package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;

public class BookingConfirmActivity extends AppCompatActivity {

    private TextView tvPickup, tvDropoff, tvDateTime, tvPassengers, tvFare;
    private Button btnHome, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_activity);

        // Initialize views
        tvPickup     = findViewById(R.id.tvPickup);
        tvDropoff    = findViewById(R.id.tvDropoff);
        tvDateTime   = findViewById(R.id.tvDateTime);
        tvPassengers = findViewById(R.id.tvPassengers);
        tvFare       = findViewById(R.id.tvFare);
        btnHome      = findViewById(R.id.btnHome);
        btnHistory   = findViewById(R.id.btnHistory);

        // Animate success icon
        ImageView imgSuccess = findViewById(R.id.imgSuccess);
        Animation bounceIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgSuccess.startAnimation(bounceIn);

        // Retrieve booking data
        Intent intent    = getIntent();
        String pickup    = intent.getStringExtra("pickup");
        String dropoff   = intent.getStringExtra("dropoff");
        String date      = intent.getStringExtra("date");
        String time      = intent.getStringExtra("time");
        String passengers = intent.getStringExtra("passengers");
        String fare      = intent.getStringExtra("fare");

        // Populate views
        if (pickup    != null) tvPickup.setText(pickup);
        if (dropoff   != null) tvDropoff.setText(dropoff);
        if (date != null && time != null)
            tvDateTime.setText(date + "  ·  " + time);
        if (passengers != null) tvPassengers.setText(passengers);
        if (fare      != null) tvFare.setText(fare);

        // Navigate home (clear back-stack back to homepage)
        btnHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(this, PassengerHomepageActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
            finish();
        });

        // View booking history
        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, BookingHistoryActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public void onBackPressed() {
        // Don't let user go back to the booking form — go home instead
        Intent homeIntent = new Intent(this, PassengerHomepageActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);
        finish();
    }
}
