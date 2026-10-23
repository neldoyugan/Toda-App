package com.example.toda.activity; // ✅ Updated package name

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.toda.R; // ✅ Updated package for R

public class SettingsDriverActivity extends AppCompatActivity {

    private TextView driverName, driverEmail, driverVehicleInfo;
    private ImageView driverProfileImage;

    private LinearLayout optionDriverEditProfile, optionVehicleSettings, optionEarnings, driverLogout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_driver); // <-- Ensure your xml name is driver_profile.xml

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        driverName = findViewById(R.id.driverName);
        driverEmail = findViewById(R.id.driverEmail);
        driverVehicleInfo = findViewById(R.id.driverVehicleInfo);
        driverProfileImage = findViewById(R.id.driverProfileImage);

        optionDriverEditProfile = findViewById(R.id.optionDriverEditProfile);
        optionVehicleSettings = findViewById(R.id.optionVehicleSettings);
        optionEarnings = findViewById(R.id.optionEarnings);
        driverLogout = findViewById(R.id.driverLogout);

        // Display Firebase User Info
        if (user != null) {
            driverEmail.setText(user.getEmail());
            driverName.setText("Driver Account"); // You can replace with Firestore data
        }

        // Click Listeners
        optionDriverEditProfile.setOnClickListener(v ->
                Toast.makeText(SettingsDriverActivity.this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        );

        optionVehicleSettings.setOnClickListener(v ->
                Toast.makeText(SettingsDriverActivity.this, "Vehicle Settings Clicked", Toast.LENGTH_SHORT).show()
        );

        optionEarnings.setOnClickListener(v ->
                Toast.makeText(SettingsDriverActivity.this, "Earnings Clicked", Toast.LENGTH_SHORT).show()
        );

        // Logout
        driverLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Toast.makeText(SettingsDriverActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SettingsDriverActivity.this, LoginActivity.class); // Change if your login class is different
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
