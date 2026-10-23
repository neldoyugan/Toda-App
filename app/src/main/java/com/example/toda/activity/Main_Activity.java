package com.example.toda.activity;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.toda.R;

public class Main_Activity extends AppCompatActivity {

    Button btnTryNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize button
        btnTryNow = findViewById(R.id.btnTryNow);

        // Add fade in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        btnTryNow.startAnimation(fadeIn);

        // Set click listener
        btnTryNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to next activity (example: BookingActivity)
                Intent intent = new Intent(Main_Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
