package com.blogspot.huydungdinh.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by HUNGDH on 9/23/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Reminder> myReminder;

    public ListViewAdapter(Context context, ArrayList<Reminder> myReminder) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myReminder = myReminder;
    }

    @Override
    public int getCount() {
        return myReminder.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = layoutInflater.inflate(R.layout.list_reminder_layout, null);
        }

        ImageView iv_icon = (ImageView)listItem.findViewById(R.id.iv_icon);
        TextView tv_subject = (TextView) listItem.findViewById(R.id.tv_subject);
        TextView tv_date = (TextView) listItem.findViewById(R.id.tv_date);
        if (myReminder.get(position).getAlarm() == 1) {
            iv_icon.setImageResource(R.drawable.ic_check);
        } else {
            iv_icon.setImageResource(R.drawable.ic_schedule);
        }
        tv_subject.setText(myReminder.get(position).getSubject());
        tv_date.setText(myReminder.get(position).toString());

        return listItem;
    }
}
