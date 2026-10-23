package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toda.R;

public class LoginActivity extends AppCompatActivity {

    Button btnDriverLogin, btnClientLogin, btnDriverSignUp, btnClientSignUp;
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize v
        logoImage = findViewById(R.id.logoImage);
        btnDriverLogin = findViewById(R.id.btnDriverLogin);
        btnClientLogin = findViewById(R.id.btnClientLogin);
        btnDriverSignUp = findViewById(R.id.btnDriverSignUp);
        btnClientSignUp = findViewById(R.id.btnClientSignUp);

        // Add fade in animation to logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImage.startAnimation(fadeIn);

        // Button actions with transitions
        btnDriverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, DriverLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnClientLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PassengerLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnDriverSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, DriverSignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        btnClientSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PassengerSignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}
