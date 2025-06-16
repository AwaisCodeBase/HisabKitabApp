package com.shera.hisabkitab;

import android.app.AlertDialog;
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

import com.shera.hisabkitab.databinding.ActivityHisabBinding;

import java.util.ArrayList;
import java.util.List;

public class HisabActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private ItemAdapter itemAdapter;
        private List<Item> itemList;
        private FloatingActionButton addButton;
        private TextView totalBudgetText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hisab);

            recyclerView = findViewById(R.id.itemRecyclerView);
            addButton = findViewById(R.id.addButton);
            totalBudgetText = findViewById(R.id.totalBudgetText);

            itemList = new ArrayList<>();
            itemAdapter = new ItemAdapter(itemList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(itemAdapter);

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
                    itemList.add(new Item(name, price));
                    itemAdapter.notifyDataSetChanged();
                    updateTotalBudget();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }

        private void updateTotalBudget() {
            double total = 0;
            for (Item item : itemList) {
                total += item.getPrice();
            }
            totalBudgetText.setText("Total: Rs. " + total);
        }

}