package com.example.toda.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DriverSignUpActivity extends AppCompatActivity {

    EditText fullNameInput, driverIdInput, contactNumberInput, emailInput,
            passwordInput, confirmPasswordInput, vehicleTypeInput, licensePlateInput;
    Button signupButton, btnAttachLicense, btnAttachOwnership, btnAttachPlate;
    TextView tvLicenseStatus, tvOwnershipStatus, tvPlateStatus;

    Uri licenseUri, ownershipUri, plateUri;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    // ── File picker launchers ──────────────────────────────────────────────
    private final ActivityResultLauncher<Intent> licensePicker =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    licenseUri = result.getData().getData();
                    tvLicenseStatus.setText("✅ " + getFileName(licenseUri));
                    btnAttachLicense.setText("📄  Driver's License ✓");
                }
            });

    private final ActivityResultLauncher<Intent> ownershipPicker =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ownershipUri = result.getData().getData();
                    tvOwnershipStatus.setText("✅ " + getFileName(ownershipUri));
                    btnAttachOwnership.setText("📄  Motorcycle Ownership ✓");
                }
            });

    private final ActivityResultLauncher<Intent> platePicker =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    plateUri = result.getData().getData();
                    tvPlateStatus.setText("✅ " + getFileName(plateUri));
                    btnAttachPlate.setText("📄  Plate Number Photo ✓");
                }
            });

    // ── Lifecycle ──────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        // Form fields
        fullNameInput       = findViewById(R.id.etfullNameInput);
        driverIdInput       = findViewById(R.id.etdriverIdInput);
        contactNumberInput  = findViewById(R.id.etcontactNumberInput);
        emailInput          = findViewById(R.id.etemailInput);
        passwordInput       = findViewById(R.id.etpasswordInput);
        confirmPasswordInput= findViewById(R.id.etconfirmPasswordInput);
        vehicleTypeInput    = findViewById(R.id.etvehicleTypeInput);
        licensePlateInput   = findViewById(R.id.etlicensePlateInput);

        // Buttons
        signupButton        = findViewById(R.id.btnsignUpButton);
        btnAttachLicense    = findViewById(R.id.btnAttachLicense);
        btnAttachOwnership  = findViewById(R.id.btnAttachOwnership);
        btnAttachPlate      = findViewById(R.id.btnAttachPlate);

        // Status labels
        tvLicenseStatus     = findViewById(R.id.tvLicenseStatus);
        tvOwnershipStatus   = findViewById(R.id.tvOwnershipStatus);
        tvPlateStatus       = findViewById(R.id.tvPlateStatus);

        auth      = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Document pickers
        btnAttachLicense.setOnClickListener(v   -> openPicker(licensePicker));
        btnAttachOwnership.setOnClickListener(v -> openPicker(ownershipPicker));
        btnAttachPlate.setOnClickListener(v     -> openPicker(platePicker));

        signupButton.setOnClickListener(v -> registerDriver());
    }

    // ── Helpers ────────────────────────────────────────────────────────────
    private void openPicker(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        launcher.launch(Intent.createChooser(intent, "Select Document"));
    }

    private String getFileName(Uri uri) {
        if (uri == null) return "Unknown file";
        String path = uri.getLastPathSegment();
        return path != null ? path : "file selected";
    }

    // ── Registration ───────────────────────────────────────────────────────
    private void registerDriver() {
        String fullName       = fullNameInput.getText().toString().trim();
        String driverID       = driverIdInput.getText().toString().trim();
        String contactNumber  = contactNumberInput.getText().toString().trim();
        String email          = emailInput.getText().toString().trim();
        String password       = passwordInput.getText().toString().trim();
        String confirmPassword= confirmPasswordInput.getText().toString().trim();
        String vehicleType    = vehicleTypeInput.getText().toString().trim();
        String licensePlate   = licensePlateInput.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(driverID)
                || TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(vehicleType) || TextUtils.isEmpty(licensePlate)) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (licenseUri == null || ownershipUri == null || plateUri == null) {
            Toast.makeText(this, "Please attach all required documents.", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String userID = auth.getCurrentUser().getUid();

                    HashMap<String, Object> driverData = new HashMap<>();
                    driverData.put("fullName",      fullName);
                    driverData.put("driverID",      driverID);
                    driverData.put("contactNumber", contactNumber);
                    driverData.put("emailAddress",  email);
                    driverData.put("vehicleType",   vehicleType);
                    driverData.put("licensePlate",  licensePlate);
                    driverData.put("licenseDoc",    licenseUri.toString());
                    driverData.put("ownershipDoc",  ownershipUri.toString());
                    driverData.put("plateDoc",      plateUri.toString());
                    driverData.put("accountStatus", "Pending");

                    DocumentReference doc = firestore.collection("drivers").document(userID);
                    doc.set(driverData)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(DriverSignUpActivity.this,
                                        "Registered! Awaiting admin approval.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DriverSignUpActivity.this, DriverHompageActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(DriverSignUpActivity.this,
                                            "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(DriverSignUpActivity.this,
                                "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
