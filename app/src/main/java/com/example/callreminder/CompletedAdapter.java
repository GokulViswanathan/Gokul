package com.example.callreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {
    private List<Reminder> list;
    public CompletedAdapter(List<Reminder> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_completed, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder r = list.get(position);
        holder.tvPhone.setText(r.phone);
        holder.tvTime.setText(DateFormat.getDateTimeInstance().format(r.timestamp));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhone, tvTime;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
