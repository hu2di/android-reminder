package com.blogspot.huydungdinh.reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by HUNGDH on 9/23/2015.
 */
public class AddActivity extends AppCompatActivity {

    private EditText et_subject, et_content;
    private TextView tv_set_time;
    private Button btn_set_time;

    private String subject, content;
    private Calendar calendar;
    private Date time;

    private boolean isNew = true;
    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.action_add));

        initUI();

        getDefaultInfo();

        try {
            Bundle data = getIntent().getExtras();
            reminder = (Reminder) data.getSerializable("reminder");
            if (reminder != null) {
                isNew = false;
                et_subject.setText(reminder.getSubject());
                et_content.setText(reminder.getContent());
                tv_set_time.setText(reminder.toString());
            }
        } catch (Exception e) {
            Log.d("huyhungdinh", "Error" + e.toString());
        }
    }

    private void initUI() {
        et_subject = (EditText) findViewById(R.id.et_subject);
        et_content = (EditText) findViewById(R.id.et_content);

        tv_set_time = (TextView) findViewById(R.id.tv_set_time);

        btn_set_time = (Button) findViewById(R.id.btn_set_time);
        btn_set_time.setOnClickListener(buttonClick);
    }

    public void getDefaultInfo() {
        calendar = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String strTime = dft.format(calendar.getTime());
        tv_set_time.setText(strTime);
        time = calendar.getTime();
    }

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_set_time:
                    dateTimeDialog();
                    break;
            }
        }
    };

    private void dateTimeDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_datetimepicker_layout, null);
        final DatePicker dp = (DatePicker) dialogView.findViewById(R.id.date_picker);
        final TimePicker tp = (TimePicker) dialogView.findViewById(R.id.time_picker);
        tp.setIs24HourView(true);

        dialog.setView(dialogView);
        dialog.setTitle(R.string.settime);
        dialog.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, dp.getYear());
                calendar.set(Calendar.MONTH, dp.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
                calendar.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
                calendar.set(Calendar.MINUTE, tp.getCurrentMinute());

                time = calendar.getTime();

                SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String strTime = dft.format(time);
                tv_set_time.setText(strTime);
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    private void onSave() {
        subject = et_subject.getText().toString();
        content = et_content.getText().toString();
        Reminder _reminder = new Reminder(subject, content, time, 0);
        ReminderHelper reminderHelper = new ReminderHelper(this);
        if (isNew) {
            long id = reminderHelper.insert(_reminder);
            _reminder.setId(id);
        } else {
            reminderHelper.update(reminder.getId(), _reminder);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent myIntent = new Intent(AddActivity.this, AlarmReceiver.class);
        myIntent.putExtra("reminder", _reminder);

        final int _id = (int) System.currentTimeMillis();
        PendingIntent appIntent = PendingIntent.getBroadcast(AddActivity.this, _id, myIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), appIntent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            onSave();
            return true;
        }

        if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
