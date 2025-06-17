package com.shera.hisabkitab;

import static android.view.View.GONE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shera.hisabkitab.databinding.ActivityHisabBinding;

import java.util.ArrayList;
import java.util.List;

public class HisabActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private FloatingActionButton addButton;
    private Button history;
    private TextView totalBudgetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisab);

        recyclerView = findViewById(R.id.itemRecyclerView);
        addButton = findViewById(R.id.addButton);
        totalBudgetText = findViewById(R.id.totalBudgetText);
        history = findViewById(R.id.btnHistory);

        // ✅ Initialize list and adapter first
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, position -> {
            itemList.remove(position);
            itemAdapter.notifyItemRemoved(position);
            updateTotalBudget();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        // ✅ Now it's safe to access intent and populate list
        String timestampKey = getIntent().getStringExtra("timestamp_key");
        if (timestampKey != null) {
            addButton.setVisibility(GONE);
            SharedPreferences prefs = getSharedPreferences("HisabPrefs", MODE_PRIVATE);
            String json = prefs.getString(timestampKey, null);
            if (json != null) {
                List<Item> loadedItems = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<Item>>() {}.getType());
                itemList.addAll(loadedItems);
                itemAdapter.notifyDataSetChanged();
                updateTotalBudget();
            }
        }

        history.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        addButton.setOnClickListener(v -> showAddItemDialog());
    }

    private void showAddItemDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_input_dialog, null);
        EditText editName = dialogView.findViewById(R.id.editItemName);
        EditText editPrice = dialogView.findViewById(R.id.editItemPrice);
        Button btnOk = dialogView.findViewById(R.id.btnOk);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        btnOk.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String priceStr = editPrice.getText().toString();

            if (!name.isEmpty() && !priceStr.isEmpty()) {
                double price = Double.parseDouble(priceStr);
                String timestamp = new java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(new java.util.Date());

                Item item = new Item(name, price, timestamp);
                itemList.add(item);
                itemAdapter.notifyDataSetChanged();
                updateTotalBudget();
                dialog.dismiss();

                // Save to SharedPreferences
                saveItemListToPrefs(timestamp, itemList);
            } else {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }

    private void saveItemListToPrefs(String timestamp, List<Item> items) {
        SharedPreferences prefs = getSharedPreferences("HisabPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        // Convert item list to JSON string
        String json = gson.toJson(items);
        editor.putString(timestamp, json);
        editor.apply();
    }

    private void updateTotalBudget() {
        double total = 0;
        for (Item item : itemList) {
            total += item.getPrice();
        }
        totalBudgetText.setText("Total: Rs. " + total);
    }

}