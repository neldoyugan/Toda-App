package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent; // ✅ Added
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class PassengerSignUpActivity extends AppCompatActivity {

    EditText fullNameInput, emailInput, passwordInput, barangayInput, purokInput, ageInput, birthdayInput;
    RadioGroup sexGroup;
    RadioButton maleOption, femaleOption;
    Button registerButton;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_sign_up_activity);

        fullNameInput = findViewById(R.id.etfullNameInput);
        emailInput = findViewById(R.id.etemailInput);
        passwordInput = findViewById(R.id.etpasswordInput);
        barangayInput = findViewById(R.id.etbarangayInput);
        purokInput = findViewById(R.id.etpurokInput);
        ageInput = findViewById(R.id.etageInput);
        birthdayInput = findViewById(R.id.etbirthdayInput);

        sexGroup = findViewById(R.id.rgSex);
        maleOption = findViewById(R.id.rbMale);
        femaleOption = findViewById(R.id.rbFemale);

        registerButton = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ✅ DATE PICKER FOR BIRTHDAY
        birthdayInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PassengerSignUpActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                        birthdayInput.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        registerButton.setOnClickListener(view -> registerClient());
    }

    private void registerClient() {
        String fullName = fullNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String barangay = barangayInput.getText().toString().trim();
        String purok = purokInput.getText().toString().trim();
        String age = ageInput.getText().toString().trim();
        String birthday = birthdayInput.getText().toString().trim();

        int selectedSex = sexGroup.getCheckedRadioButtonId();
        String sex = selectedSex == maleOption.getId() ? "Male" : (selectedSex == femaleOption.getId() ? "Female" : "");

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || barangay.isEmpty() ||
                purok.isEmpty() || age.isEmpty() || birthday.isEmpty() || sex.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    String uid = result.getUser().getUid();

                    HashMap<String, Object> client = new HashMap<>();
                    client.put("fullName", fullName);
                    client.put("emailAddress", email);
                    client.put("barangay", barangay);
                    client.put("purok", purok);
                    client.put("age", age);
                    client.put("birthday", birthday);
                    client.put("sex", sex);

                    db.collection("clients").document(uid).set(client)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                // ✅ Redirect to Passenger Home Page
                                Intent intent = new Intent(PassengerSignUpActivity.this, PassengerHomepageActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
