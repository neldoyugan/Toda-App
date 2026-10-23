package com.example.toda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePassengerActivity extends AppCompatActivity {

    private ImageView imageProfile;
    private TextView textFullName, textEmail, textBarangay, textPurok, textAge, textBirthday, textPassword;
    private RadioGroup radioGroupSex;
    private RadioButton radioMale, radioFemale;
    private Button btnEdit, btnLogout;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_passenger_activity);

        imageProfile = findViewById(R.id.imageProfile);
        textFullName = findViewById(R.id.textFullName);
        textEmail = findViewById(R.id.textEmail);
        textBarangay = findViewById(R.id.textBarangay);
        textPurok = findViewById(R.id.textPurok);
        textAge = findViewById(R.id.textAge);
        textBirthday = findViewById(R.id.textBirthday);
        textPassword = findViewById(R.id.textPassword);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, PassengerLoginActivity.class));
            finish();
            return;
        }

        loadClientProfile();

        // Edit Profile → navigate to EditPassengerProfileActivity
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePassengerActivity.this, EditPassengerProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfilePassengerActivity.this, PassengerLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadClientProfile() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("clients").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        textFullName.setText(documentSnapshot.getString("fullName"));
                        textEmail.setText(documentSnapshot.getString("emailAddress"));
                        textBarangay.setText(documentSnapshot.getString("barangay"));
                        textPurok.setText(documentSnapshot.getString("purok"));
                        textAge.setText(documentSnapshot.getString("age"));
                        textBirthday.setText(documentSnapshot.getString("birthday"));

                        String sex = documentSnapshot.getString("sex");
                        if (sex != null) {
                            if (sex.equals("Male")) radioMale.setChecked(true);
                            else if (sex.equals("Female")) radioFemale.setChecked(true);
                        }
                    } else {
                        textFullName.setText("Profile not found");
                    }
                })
                .addOnFailureListener(e ->
                        textFullName.setText("Failed to load profile"));
    }
}
