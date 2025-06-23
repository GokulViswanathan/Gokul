package com.example.callreminder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ReminderAdapter adapter;
    private List<Reminder> reminders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReminderAdapter(reminders, r -> {
            dbHelper.markCompleted(r.id);
            loadReminders();
        });
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_add).setOnClickListener(v ->
                startActivity(new Intent(this, SetReminderActivity.class)));

        findViewById(R.id.btn_completed).setOnClickListener(v ->
                startActivity(new Intent(this, CompletedRemindersActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReminders();
    }

    private void loadReminders() {
        reminders.clear();
        reminders.addAll(dbHelper.getReminders(0));
        adapter.notifyDataSetChanged();
    }
}
