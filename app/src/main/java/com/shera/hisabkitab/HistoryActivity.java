package com.shera.hisabkitab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shera.hisabkitab.databinding.ActivityHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<String> timestamps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load all items from storage
        List<Item> allItems = ItemStorage.loadItems(this);
        timestamps = new ArrayList<>();

        if (allItems != null) {
            for (Item item : allItems) {
                if (!timestamps.contains(item.getTimestamp())) {
                    timestamps.add(item.getTimestamp());
                }
            }
        }

        // Setup RecyclerView
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter(timestamps,
                (timestamp, position) -> {
                    // Handle item click
                    Intent intent = new Intent(this, HisabActivity.class);
                    intent.putExtra("timestamp_key", timestamp);
                    startActivity(intent);
                },
                (timestamp, position) -> {
                    // Handle delete click
                    SharedPreferences prefs = getSharedPreferences("HisabPrefs", MODE_PRIVATE);
                    prefs.edit().remove(timestamp).apply();

                    adapter.removeItem(position);
                    Toast.makeText(this, "Deleted: " + timestamp, Toast.LENGTH_SHORT).show();
                }
        );


        binding.historyRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Loaded: " + timestamps.size(), Toast.LENGTH_SHORT).show();
    }
}
