package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class PassengerLoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private TextView tvForgot;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_login_activity);

        etEmail = findViewById(R.id.etDriverID);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister); // ✅ FIXED
        tvForgot = findViewById(R.id.tvForgot);
        ImageView imgLogo = findViewById(R.id.imgLogo);

        auth = FirebaseAuth.getInstance();

        // Add fade in animation to logo
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgLogo.startAnimation(fadeIn);

        btnLogin.setOnClickListener(v -> loginPassenger());

        btnRegister.setOnClickListener(v -> {
            // ✅ Open Passenger Sign Up page
            Intent i = new Intent(PassengerLoginActivity.this, PassengerSignUpActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        tvForgot.setOnClickListener(v -> {
            Intent forgotIntent = new Intent(PassengerLoginActivity.this, ForgotPassengerPasswordActivity.class);
            startActivity(forgotIntent);
        });
    }

    private void loginPassenger() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PassengerLoginActivity.this, PassengerHomepageActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
