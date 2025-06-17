package com.shera.hisabkitab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    // Callback interface for item click
    public interface OnItemClickListener {
        void onItemClick(String timestamp, int position);
    }

    // Callback interface for delete click
    public interface OnDeleteClickListener {
        void onDeleteClick(String timestamp, int position);
    }

    private List<String> timestamps;
    private final OnItemClickListener itemClickListener;
    private final OnDeleteClickListener deleteClickListener;

    // Constructor
    public HistoryAdapter(List<String> timestamps, OnItemClickListener itemClickListener, OnDeleteClickListener deleteClickListener) {
        this.timestamps = timestamps;
        this.itemClickListener = itemClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String timestamp = timestamps.get(position);
        holder.timestampText.setText(timestamp);

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(timestamp, position));
        holder.delBtn.setOnClickListener(v -> deleteClickListener.onDeleteClick(timestamp, position));
    }

    @Override
    public int getItemCount() {
        return timestamps.size();
    }

    public void removeItem(int position) {
        timestamps.remove(position);
        notifyItemRemoved(position);
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timestampText;
        ImageView delBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            timestampText = itemView.findViewById(R.id.timestampTextView); // Make sure this ID is defined in history_row.xml
            delBtn = itemView.findViewById(R.id.delbtn);                   // Make sure this ID is defined in history_row.xml
        }
    }
}
