package com.blogspot.huydungdinh.reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by HUNGDH on 10/7/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Reminder reminder;
        try {
            Bundle bundle = intent.getExtras();
            reminder = (Reminder) bundle.get("statusSchedule");
            Toast.makeText(context, reminder.getSubject(), Toast.LENGTH_SHORT).show();
            Log.d("huyhungdinh", "Receive: " + reminder.toString());
        } catch (Exception e) {
            Log.d("huyhungdinh", "Error Receiver: " + e.toString());
        }

       /* Intent intent1 = new Intent(context, AlarmClock.ACTION_SHOW_ALARMS);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent i = new Intent(MainActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/
    }
}
