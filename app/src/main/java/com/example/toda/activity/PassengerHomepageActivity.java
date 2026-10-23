package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PassengerHomepageActivity extends AppCompatActivity {

    private CardView btnBookRide, btnBookingHistory, btnProfile, btnNotifications;
    private TextView tvUserName;
    private ImageView btnSettingsTop, btnBack, imgLogo;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_homepage);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI
        tvUserName = findViewById(R.id.tvUserName);
        btnBookRide = findViewById(R.id.btnBookRide);
        btnBookingHistory = findViewById(R.id.btnBookingHistory);
        btnProfile = findViewById(R.id.btnProfile);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnSettingsTop = findViewById(R.id.btnSettingsTop);
        btnBack = findViewById(R.id.btnBack);
        imgLogo = findViewById(R.id.imgLogo);

        // Add fade-in animation to logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgLogo.startAnimation(fadeIn);

        // Get the current logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("Passengers").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        tvUserName.setText("Welcome, " + (username != null ? username : "Passenger") + "!");
                    } else {
                        tvUserName.setText("Welcome, Passenger!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    tvUserName.setText("Welcome, Passenger!");
                }
            });
        } else {
            startActivity(new Intent(PassengerHomepageActivity.this, LoginActivity.class));
            finish();
        }

        // Settings button → SettingsPassengerActivity
        btnSettingsTop.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsPassengerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Back button → LoginActivity (logout)
        btnBack.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Navigation cards
        btnBookRide.setOnClickListener(v ->
                startActivity(new Intent(this, BookRideActivity.class)));

        btnBookingHistory.setOnClickListener(v ->
                startActivity(new Intent(this, BookingHistoryActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfilePassengerActivity.class)));

        btnNotifications.setOnClickListener(v ->
                Toast.makeText(this, "Notifications coming soon!", Toast.LENGTH_SHORT).show());
    }
}
