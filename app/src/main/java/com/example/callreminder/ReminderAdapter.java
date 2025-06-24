package com.example.callreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    public interface OnCompleteListener {
        void onComplete(Reminder reminder);
    }

    private List<Reminder> list;
    private OnCompleteListener listener;

    public ReminderAdapter(List<Reminder> list, OnCompleteListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder r = list.get(position);
        holder.tvPhone.setText(r.phone);
        holder.tvTime.setText(DateFormat.getDateTimeInstance().format(r.timestamp));
        holder.btnComplete.setOnClickListener(v -> {
            if (listener != null) listener.onComplete(r);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhone, tvTime;
        Button btnComplete;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnComplete = itemView.findViewById(R.id.btn_complete);
        }
    }
}
