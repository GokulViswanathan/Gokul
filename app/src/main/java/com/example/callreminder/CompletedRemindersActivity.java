package com.example.callreminder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CompletedRemindersActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private CompletedAdapter adapter;
    private List<Reminder> reminders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        dbHelper = new DatabaseHelper(this);
        RecyclerView rv = findViewById(R.id.recyclerViewCompleted);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompletedAdapter(reminders);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reminders.clear();
        reminders.addAll(dbHelper.getReminders(1));
        adapter.notifyDataSetChanged();
    }
}
