package com.blogspot.huydungdinh.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HUNGDH on 10/7/2015.
 */
public class ReminderHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminder.db";
    private static final int SCHEMA_VERSION = 1;

    public ReminderHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE if not exists tbl_reminder " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "subject TEXT, content TEXT, time DATETIME, alarm INTEGER) ;";
        Log.d("huyhungdinh", "onCreate, sqlCommand: " + sqlCommand);
        db.execSQL(sqlCommand);
    }

    public long insert(Reminder reminder) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String subject = reminder.getSubject(), content = reminder.getContent();
        String time = reminder.getStandard();
        int alarm = reminder.getAlarm();
        cv.put("subject", subject);
        cv.put("content", content);
        cv.put("time", time);
        cv.put("alarm", alarm);

        long result = db.insert("tbl_reminder", null, cv);
        Log.d("huyhungdinh", "Insert DB: id = " + result);
        return result;
    }

    public long update(long id, Reminder reminder) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        String subject = reminder.getSubject(), content = reminder.getContent();
        String time = reminder.getStandard();
        int alarm = reminder.getAlarm();

        cv.put("subject", subject);
        cv.put("content", content);
        cv.put("time", time);
        cv.put("alarm", alarm);
        String[] args = {String.valueOf(id)};
        long nb = db.update("tbl_reminder", cv, "_id=?", args);
        Log.d("myLog","Update: " + nb);
        return  nb;
    }

    public long delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(id)};
        long result = db.delete("tbl_reminder", "_id=?", args);
        Log.d("huyhungdinh", "Delete: id = " + result);
        return result;
    }

    public Cursor getAll() {
        SQLiteDatabase db = getReadableDatabase();
        String sqlCommand = "SELECT * FROM tbl_reminder";
        Cursor result = db.rawQuery(sqlCommand, null);
        return result;
    }

    public ArrayList<Reminder> showAll(Cursor result) {
        ArrayList<Reminder> list = new ArrayList<Reminder>();
        result.moveToFirst();
        while (!result.isAfterLast()) {
            long id = result.getInt(0);
            String subject = result.getString(1);
            String content = result.getString(2);
            String _time = result.getString(3);
            Date time = new Date();
            try {
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time = sdf.parse(_time);
            } catch (Exception e) {
                Log.d("huyhungdinh", "Error");
            }
            int alarm = result.getInt(4);
            Reminder reminder = new Reminder(id, subject, content, time, alarm);
            list.add(reminder);
            Log.d("myLog", id + " " + subject + " " + content + " " + time + " " + alarm);
            result.moveToNext();
        }
        return list;
    }

    public long getCount(Cursor result) {
        return result.getCount();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
