package com.example.toda.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.toda.R;

public class DriverEarningsActivity extends AppCompatActivity {

    private TextView tvTotalEarnings, tvTodayEarnings, tvWeekEarnings, tvMonthEarnings;
    private Button btnWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_earnings_activity);

        tvTotalEarnings = findViewById(R.id.tvTotalEarnings);
        tvTodayEarnings = findViewById(R.id.tvTodayEarnings);
        tvWeekEarnings = findViewById(R.id.tvWeekEarnings);
        tvMonthEarnings = findViewById(R.id.tvMonthEarnings);
        btnWithdraw = findViewById(R.id.btnWithdraw);

        // Example values: Replace with actual database values later
        tvTotalEarnings.setText("₱ 950.00");
        tvTodayEarnings.setText("Today: ₱ 120");
        tvWeekEarnings.setText("This Week: ₱ 350");
        tvMonthEarnings.setText("This Month: ₱ 950");

        btnWithdraw.setOnClickListener(v -> {
            // Action here, e.g., open Withdraw Activity
        });
    }
}
