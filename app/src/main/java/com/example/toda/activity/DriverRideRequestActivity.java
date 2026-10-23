package com.example.toda.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toda.R;
import com.example.toda.model.RideRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class DriverRideRequestActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView tv_destination, tv_estimatedTime, tv_estimatedFare, tv_passengerCount;
    RecyclerView rvChatMessages;
    EditText etMessage;
    ImageView btnSend;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_ride_request);

        tv_destination = findViewById(R.id.tv_destination);
        tv_estimatedTime = findViewById(R.id.tv_estimatedTime);
        tv_estimatedFare = findViewById(R.id.tv_estimatedFare);
        tv_passengerCount = findViewById(R.id.tv_passengerCount);
        rvChatMessages = findViewById(R.id.rvChatMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        rvChatMessages.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Replace with real data from Firebase
        RideRequest request = new RideRequest("Public Market", "Town Plaza", "₱40", "2", "6 mins", "Ongoing");

        tv_destination.setText("Destination: " + request.destination);
        tv_estimatedTime.setText("Estimated Time: " + request.timeEstimate);
        tv_estimatedFare.setText("Fare: " + request.fare);
        tv_passengerCount.setText("Passengers: " + request.passengerCount);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
