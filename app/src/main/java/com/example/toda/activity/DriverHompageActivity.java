package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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

public class DriverHompageActivity extends AppCompatActivity {

    CardView boxRideRequests, boxCurrentRides, boxEarnings;
    ImageView btnSettingsTop, btnBack, logoImage;
    TextView tvWelcome;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_hompage);

        // Initialize views
        boxRideRequests = findViewById(R.id.boxRideRequests);
        boxCurrentRides = findViewById(R.id.boxCurrentRides);
        boxEarnings = findViewById(R.id.boxEarnings);
        btnSettingsTop = findViewById(R.id.btnSettingsTop);
        btnBack = findViewById(R.id.btnBack);
        logoImage = findViewById(R.id.logoImage);
        tvWelcome = findViewById(R.id.tvWelcome);

        mAuth = FirebaseAuth.getInstance();

        // Animate logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(fadeIn);

        // Load driver welcome name from Firebase
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference driverRef = FirebaseDatabase.getInstance()
                    .getReference("Drivers").child(currentUser.getUid());
            driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("username").getValue(String.class);
                        tvWelcome.setText("Welcome, " + (name != null ? name : "Driver") + "!");
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) { }
            });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        // Back button → go back
        btnSettingsTop.setOnClickListener(v -> onBackPressed());

        // Settings button → SettingsDriverActivity
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsDriverActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Card navigation
        boxRideRequests.setOnClickListener(v ->
                startActivity(new Intent(this, DriverHistoryActivity.class)));

        boxCurrentRides.setOnClickListener(v ->
                startActivity(new Intent(this, DriverRideRequestActivity.class)));

        boxEarnings.setOnClickListener(v ->
                startActivity(new Intent(this, DriverEarningsActivity.class)));
    }
}
