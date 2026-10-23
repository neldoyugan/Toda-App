package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotDriverPasswordActivity extends AppCompatActivity {

    private EditText etDriverEmail;
    private Button btnDriverResetLink;
    private TextView tvDriverBackLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_driver_password_activity);

        etDriverEmail = findViewById(R.id.etDriverEmail);
        btnDriverResetLink = findViewById(R.id.btnDriverResetLink);
        tvDriverBackLogin = findViewById(R.id.tvDriverBackLogin);
        mAuth = FirebaseAuth.getInstance();

        btnDriverResetLink.setOnClickListener(v -> sendResetLink());
        tvDriverBackLogin.setOnClickListener(v -> finish());
    }

    private void sendResetLink() {
        String email = etDriverEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etDriverEmail.setError("This field is required");
            etDriverEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Reset link sent! Check your email.", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
