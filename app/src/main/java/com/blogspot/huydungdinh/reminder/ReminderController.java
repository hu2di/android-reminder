package com.blogspot.huydungdinh.reminder;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

/**
 * Created by HUNGDH on 9/23/2015.
 */
public class ReminderController {

    private Vector<Reminder> myReminder;
    private String filename = "reminder_file.dat";
    private Context context;

    public ReminderController(Context context) {
        this.context = context;
        myReminder = new Vector<Reminder>();
    }

    public void docFile() {
        try {
            File f = new File(filename);
            FileInputStream fIn = context.openFileInput(f.getPath());
            ObjectInputStream oIn = new ObjectInputStream(fIn);
            myReminder = (Vector<Reminder>) oIn.readObject();
            oIn.close();
            fIn.close();
            Log.d("huyhungdinh", "Readed");
        } catch (FileNotFoundException e) {
            ghiFile();
        } catch (Exception e) {
            Log.d("huyhungdinh", "Error Read File: " + e.toString());
        }
    }

    public void ghiFile() {
        try {
            File f = new File(context.getFilesDir(), filename);
            f.createNewFile();
            FileOutputStream fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oOut = new ObjectOutputStream(fOut);
            oOut.writeObject(myReminder);
            oOut.close();
            fOut.close();
            Log.d("myLog", "Writed");
        } catch (Exception e) {
            Log.d("myLog", "Error Write File" + e.toString());
        }
    }

    public void addReminder(Reminder reminder) {
        try {
            myReminder.add(reminder);
        } catch (Exception e) {
            Log.d("huyhungdinh", "Error add Status" + e.toString());
        }
    }

    public void deleteReminder(Reminder reminder) {
        for (int i = 0; i < myReminder.size(); i++) {
            if (myReminder.get(i).getTime().equals(reminder.getTime())) {
                myReminder.remove(i);
                return;
            }
        }
    }

    public Reminder getReminder(Reminder reminder) {
        for (int i = 0; i < myReminder.size(); i++) {
            if (myReminder.get(i).getTime().equals(reminder.getTime())) {
                return  myReminder.get(i);
            }
        }
        return null;
    }

    public void setReminder(Reminder reminder) {
        for (int i = 0; i < myReminder.size(); i++) {
            if (myReminder.get(i).getTime().equals(reminder.getTime())) {
                myReminder.set(i, reminder);
            }
        }
    }

    public Vector<Reminder> getAll() {
        return myReminder;
    }
}
