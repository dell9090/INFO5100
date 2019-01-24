package com.example.xin.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class NotificationAdapter extends BaseAdapter {


    private LinkedList<Notification> mData;
    private Context mContext;

    public NotificationAdapter(LinkedList<Notification> mData, Context mContext) {
        this.mData = mData;
        Log.d("test",mData.get(0).getContent());
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;


        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
            holder = new ViewHolder();
            holder.txt_date = (TextView) convertView.findViewById(R.id.notification_date);
            holder.txt_content = (TextView) convertView.findViewById(R.id.notification_content);
            holder.txt_title = (TextView) convertView.findViewById(R.id.notification_title);
            convertView.setTag(holder);   //将Holder存储到convertView中

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_date.setText(mData.get(position).getDate());
        holder.txt_content.setText(mData.get(position).getContent());
        holder.txt_title.setText(mData.get(position).getTitle());

        return convertView;
    }

    static class ViewHolder {
        TextView txt_date;
        TextView txt_content;
        TextView txt_title;
    }

}
