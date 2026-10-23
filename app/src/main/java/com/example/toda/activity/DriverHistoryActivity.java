package com.example.toda.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.toda.R;
import com.example.toda.model.DriverHistoryAdapter;
import com.example.toda.model.TripHistory;

import java.util.ArrayList;
import java.util.List;

public class DriverHistoryActivity extends AppCompatActivity {

    RecyclerView rvDriverHistory;
    DriverHistoryAdapter adapter;
    List<TripHistory> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_driver_activity);

        rvDriverHistory = findViewById(R.id.rvDriverHistory);
        rvDriverHistory.setLayoutManager(new LinearLayoutManager(this));

        historyList = new ArrayList<>();
        historyList.add(new TripHistory("City Mall", "₱120", "Feb 1, 2025"));
        historyList.add(new TripHistory("Plaza", "₱80", "Feb 2, 2025"));
        historyList.add(new TripHistory("Hospital", "₱60", "Feb 4, 2025"));

        adapter = new DriverHistoryAdapter(historyList);
        rvDriverHistory.setAdapter(adapter);
    }
}
