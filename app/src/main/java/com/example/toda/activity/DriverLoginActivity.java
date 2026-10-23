package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginActivity extends AppCompatActivity {

    private TextInputEditText etDriverID, etPassword;
    private Button btnLogin, btnRegister;
    private TextView tvForgot;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        etDriverID = findViewById(R.id.etDriverID);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvForgot = findViewById(R.id.tvForgot);

        auth = FirebaseAuth.getInstance();

        // Login button — authenticate with Firebase
        btnLogin.setOnClickListener(v -> {
            String email = etDriverID.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etDriverID.setError("Email is required");
                etDriverID.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(DriverLoginActivity.this, "Login Successful! 🛵", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DriverLoginActivity.this, DriverHompageActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(DriverLoginActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });

        // Register button
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(DriverLoginActivity.this, DriverSignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Forgot password
        tvForgot.setOnClickListener(v -> {
            Intent intent = new Intent(DriverLoginActivity.this, ForgotDriverPasswordActivity.class);
            startActivity(intent);
        });
    }
}
