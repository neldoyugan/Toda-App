package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsPassengerActivity extends AppCompatActivity {

    private TextView userName, userEmail;
    private LinearLayout optionEditProfile, optionNotifications, optionPrivacy, optionAbout, optionLogout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_passenger_activity);

        // Initialize views — all IDs now exist in the updated XML
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        optionEditProfile = findViewById(R.id.optionEditProfile);
        optionNotifications = findViewById(R.id.optionNotifications);
        optionPrivacy = findViewById(R.id.optionPrivacy);
        optionAbout = findViewById(R.id.optionAbout);
        optionLogout = findViewById(R.id.optionLogout);

        mAuth = FirebaseAuth.getInstance();

        // Load user info from Firebase
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userEmail.setText(currentUser.getEmail());
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("Passengers").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("username").getValue(String.class);
                        if (name != null) userName.setText(name);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) { }
            });
        }

        // Click listeners
        optionEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsPassengerActivity.this, EditPassengerProfileActivity.class);
            startActivity(intent);
        });

        optionNotifications.setOnClickListener(v ->
                Toast.makeText(this, "Notifications coming soon!", Toast.LENGTH_SHORT).show());

        optionPrivacy.setOnClickListener(v ->
                Toast.makeText(this, "Privacy Policy coming soon!", Toast.LENGTH_SHORT).show());

        optionAbout.setOnClickListener(v ->
                Toast.makeText(this, "Maribojoc TODA E-Booking v1.0", Toast.LENGTH_SHORT).show());

        optionLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout Confirmation")
                .setMessage("Do you really want to logout?")
                .setPositiveButton("Yes, Logout", (dialog, which) -> {
                    mAuth.signOut();
                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SettingsPassengerActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
