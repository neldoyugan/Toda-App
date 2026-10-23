package com.example.toda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDriverActivity extends AppCompatActivity {

    TextView tvName, tvDriverID, tvContact, tvEmail, tvVehicle, tvPlate, tvStatus;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_driver);

        tvName = findViewById(R.id.tvName);
        tvDriverID = findViewById(R.id.tvDriverID);
        tvContact = findViewById(R.id.tvContact);
        tvEmail = findViewById(R.id.tvEmail);
        tvVehicle = findViewById(R.id.tvVehicle);
        tvPlate = findViewById(R.id.tvPlate);
        tvStatus = findViewById(R.id.tvStatus);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        loadDriverData();
    }

    private void loadDriverData() {

        String userID = auth.getCurrentUser().getUid(); // **THIS IS THE CONNECTOR (UID)**

        firestore.collection("drivers").document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        tvName.setText(documentSnapshot.getString("fullName"));
                        tvDriverID.setText(documentSnapshot.getString("driverID"));
                        tvContact.setText(documentSnapshot.getString("contactNumber"));
                        tvEmail.setText(documentSnapshot.getString("emailAddress"));
                        tvVehicle.setText(documentSnapshot.getString("vehicleType"));
                        tvPlate.setText(documentSnapshot.getString("licensePlate"));
                        tvStatus.setText(documentSnapshot.getString("accountStatus"));

                    } else {
                        Toast.makeText(this, "No Profile Data Found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to Load Data.", Toast.LENGTH_SHORT).show();
                });
    }
}
