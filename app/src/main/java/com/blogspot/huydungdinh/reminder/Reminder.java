package com.blogspot.huydungdinh.reminder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HUNGDH on 9/23/2015.
 */
public class Reminder implements Serializable{
    private long id;
    private String subject;
    private String content;
    private Date time;
    private int alarm;

    public Reminder() {

    }

    public Reminder(String subject, String content, Date time, int alarm) {
        this.subject = subject;
        this.content = content;
        this.time = time;
        this.alarm = alarm;
    }

    public Reminder(long id, String subject, String content, Date time, int alarm) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.time = time;
        this.alarm = alarm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getDateFormat() {
        String result;
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(time);
        return result;
    }

    public String getTimeFormat() {
        String result;
        String strDateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(time);
        return result;
    }

    public String getStandard() {
        String result;
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(time);
        return result;
    }

    @Override
    public String toString() {
        return getDateFormat() + "  " + getTimeFormat();
    }
}
