package com.example.callreminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {

    private EditText etPhone;
    private long selectedTime = 0L;
    private DatabaseHelper dbHelper;

    private final ActivityResultLauncher<Intent> contactPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri contactUri = result.getData().getData();
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                    try (var cursor = getContentResolver().query(contactUri, projection, null, null, null)) {
                        if (cursor != null && cursor.moveToFirst()) {
                            String number = cursor.getString(0);
                            etPhone.setText(number);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        dbHelper = new DatabaseHelper(this);
        etPhone = findViewById(R.id.et_phone);
        Button btnPickContact = findViewById(R.id.btn_pick_contact);
        Button btnPickTime = findViewById(R.id.btn_pick_time);
        Button btnSave = findViewById(R.id.btn_save);

        btnPickContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            contactPicker.launch(intent);
        });

        btnPickTime.setOnClickListener(v -> showDateTimeDialog());

        btnSave.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            if (phone.isEmpty() || selectedTime == 0L) {
                Toast.makeText(this, "Please pick contact and time", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = dbHelper.insertReminder(phone, selectedTime);
            scheduleAlarm(id, selectedTime, phone);
            finish();
        });
    }

    private void showDateTimeDialog() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            now.set(Calendar.YEAR, year);
            now.set(Calendar.MONTH, month);
            now.set(Calendar.DAY_OF_MONTH, day);
            new TimePickerDialog(this, (tView, hour, minute) -> {
                now.set(Calendar.HOUR_OF_DAY, hour);
                now.set(Calendar.MINUTE, minute);
                now.set(Calendar.SECOND, 0);
                selectedTime = now.getTimeInMillis();
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false).show();
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void scheduleAlarm(long id, long time, String phone) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("phone", phone);
        intent.putExtra("id", id);
        PendingIntent pi = PendingIntent.getBroadcast(this, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (am != null) {
            am.setExact(AlarmManager.RTC_WAKEUP, time, pi);
        }
    }
}
