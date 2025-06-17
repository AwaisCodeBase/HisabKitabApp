package com.shera.hisabkitab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> items;
    private OnItemDeleteListener deleteListener;

    // Constructor
    public ItemAdapter(List<Item> items, OnItemDeleteListener deleteListener) {
        this.items = items;
        this.deleteListener = deleteListener;
    }

    // ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView delBtn;
        TextView nameText, priceText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.itemName);
            priceText = itemView.findViewById(R.id.itemPrice);
            delBtn = itemView.findViewById(R.id.delbtn);
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

        holder.delBtn.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Delete interface
    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }
}
