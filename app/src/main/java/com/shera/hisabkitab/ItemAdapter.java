package com.shera.hisabkitab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.itemName);
            priceText = itemView.findViewById(R.id.itemPrice);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item current = items.get(position);
        holder.nameText.setText(current.getName());
        holder.priceText.setText("Rs. " + current.getPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
