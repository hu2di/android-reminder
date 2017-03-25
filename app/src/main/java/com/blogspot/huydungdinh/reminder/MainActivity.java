package com.blogspot.huydungdinh.reminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private ListView lv_reminder;
    private ReminderHelper reminderHelper;
    private ArrayList<Reminder> myReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initUI() {
        lv_reminder = (ListView) findViewById(R.id.lv_reminder);
        reminderHelper = new ReminderHelper(this);
        Cursor cursor = reminderHelper.getAll();
        myReminder = reminderHelper.showAll(cursor);
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, myReminder);
        lv_reminder.setAdapter(listViewAdapter);
        lv_reminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(myReminder.get(position));
                //new callReminder().execute(myReminder.get(position));
            }
        });
        lv_reminder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //dialog(myReminder.get(position));
                return false;
            }
        });
    }

    private void dialog(final Reminder reminder) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(reminder.getSubject());
        dialog.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (reminderHelper.delete(reminder.getId()) != -1) {
                    myReminder.remove(reminder);
                    ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this, myReminder);
                    lv_reminder.setAdapter(listViewAdapter);
                }
            }
        });
        dialog.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new callReminder().execute(reminder);
            }
        });
        dialog.show();
    }

    private class callReminder extends AsyncTask<Reminder, Void, Void> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "", "Đang tải... ");
            dialog.setCancelable(true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Reminder... params) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("reminder", (Serializable) params[0]);
            startActivity(intent);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initUI();
    }
}
