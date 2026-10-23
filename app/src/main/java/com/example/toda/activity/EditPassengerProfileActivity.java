package com.example.toda.activity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;

public class EditPassengerProfileActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etBarangay, etPurok, etAge, etBirthday;
    RadioGroup rgSex;
    RadioButton rbMale, rbFemale;
    Button btnCancelEdit, btnSaveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_passenger_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etBarangay = findViewById(R.id.etBarangay);
        etPurok = findViewById(R.id.etPurok);
        etAge = findViewById(R.id.etAge);
        etBirthday = findViewById(R.id.etBirthday);
        rgSex = findViewById(R.id.rgSex);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

        // Cancel button closes screen
        btnCancelEdit.setOnClickListener(v -> finish());

        // Save button retrieves inputs
        btnSaveEdit.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String barangay = etBarangay.getText().toString().trim();
            String purok = etPurok.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String birthday = etBirthday.getText().toString().trim();
            String sex = rbMale.isChecked() ? "Male" : "Female";

            // Temporarily show a message (you can replace this with Firebase or Database save)
            Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}
