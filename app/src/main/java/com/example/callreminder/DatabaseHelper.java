package com.example.callreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "reminders.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reminders (id INTEGER PRIMARY KEY AUTOINCREMENT, phone TEXT, timestamp INTEGER, status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }

    public long insertReminder(String phone, long time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phone", phone);
        cv.put("timestamp", time);
        cv.put("status", 0);
        return db.insert("reminders", null, cv);
    }

    public List<Reminder> getReminders(int status) {
        List<Reminder> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("reminders", null, "status=?", new String[]{String.valueOf(status)}, null, null, "timestamp ASC");
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndexOrThrow("id"));
            String phone = c.getString(c.getColumnIndexOrThrow("phone"));
            long ts = c.getLong(c.getColumnIndexOrThrow("timestamp"));
            int st = c.getInt(c.getColumnIndexOrThrow("status"));
            list.add(new Reminder(id, phone, ts, st));
        }
        c.close();
        return list;
    }

    public void markCompleted(long id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", 1);
        db.update("reminders", cv, "id=?", new String[]{String.valueOf(id)});
    }
}
