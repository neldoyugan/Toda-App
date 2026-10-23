package com.example.toda.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toda.R;

public class EditDriverProfileActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView profileFullName, profileStatus;
    EditText editFullName, editDriverID, editContactNumber, editEmailAddress, editVehicleType, editLicensePlate;
    Button btnLicense, btnOwnership, btnPlatePhoto, btnSaveProfile, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_driver_profile);

        // Image & Labels
        profileImage = findViewById(R.id.profileImage);
        profileFullName = findViewById(R.id.profileFullName);
        profileStatus = findViewById(R.id.profileStatus);

        // Editable fields
        editFullName = findViewById(R.id.editFullName);
        editDriverID = findViewById(R.id.editDriverID);
        editContactNumber = findViewById(R.id.editContactNumber);
        editEmailAddress = findViewById(R.id.editEmailAddress);
        editVehicleType = findViewById(R.id.editVehicleType);
        editLicensePlate = findViewById(R.id.editLicensePlate);

        // Document Buttons
        btnLicense = findViewById(R.id.btnLicense);
        btnOwnership = findViewById(R.id.btnOwnership);
        btnPlatePhoto = findViewById(R.id.btnPlatePhoto);

        // Save & Cancel
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnCancel = findViewById(R.id.btnCancel);

        // Cancel Button Action
        btnCancel.setOnClickListener(v -> finish());

        // Save Button Action (example)
        btnSaveProfile.setOnClickListener(v -> {
            String name = editFullName.getText().toString();
            String driverId = editDriverID.getText().toString();
            String contact = editContactNumber.getText().toString();
            String email = editEmailAddress.getText().toString();
            String vehicle = editVehicleType.getText().toString();
            String plate = editLicensePlate.getText().toString();

            // TODO: Save to database / server
        });

        // Document Buttons (example actions)
        btnLicense.setOnClickListener(v -> {
            // TODO: Open image picker/view
        });

        btnOwnership.setOnClickListener(v -> {
            // TODO: Open image picker/view
        });

        btnPlatePhoto.setOnClickListener(v -> {
            // TODO: Open image picker/view
        });
    }
}
