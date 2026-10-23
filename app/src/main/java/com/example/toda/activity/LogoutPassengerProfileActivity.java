package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutPassengerProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView userName, userEmail;
    private Button logoutBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_passenger_activity);

        // Firebase Instance
        mAuth = FirebaseAuth.getInstance();

        // Views
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        logoutBtn = findViewById(R.id.logoutBtn);

        // Load Data Before Display
        loadPassengerDetails();

        // Logout Button Action
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();

            Intent intent = new Intent(LogoutPassengerProfileActivity.this, PassengerLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });
    }

    private void loadPassengerDetails() {
        // Example only — replace with real data from Firebase or SharedPreferences
        userName.setText("Juan Dela Cruz");
        userEmail.setText("juan.delacruz@example.com");

        // If you want to load profile image from Firebase Storage or server
        // Use Picasso or Glide here
        // Glide.with(this).load(imageUrl).into(profileImage);
    }
}
