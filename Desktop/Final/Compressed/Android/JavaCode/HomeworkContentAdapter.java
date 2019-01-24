package com.example.xin.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class HomeworkContentAdapter extends BaseAdapter {


    private LinkedList<HomeworkContent> mData;
    private Context mContext;

    public HomeworkContentAdapter(LinkedList<HomeworkContent> mData, Context mContext) {
        this.mData = mData;
        Log.d("test",mData.get(0).getDescription());
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

            convertView = LayoutInflater.from(mContext).inflate(R.layout.homework_item, parent, false);
            holder = new HomeworkContentAdapter.ViewHolder();
            holder.txt_title = (TextView) convertView.findViewById(R.id.homework_title);
            holder.txt_content = (TextView) convertView.findViewById(R.id.homework_content);
            holder.txt_open = (TextView)convertView.findViewById(R.id.opentime);
            holder.txt_close = (TextView)convertView.findViewById(R.id.closetime);
            convertView.setTag(holder);   //将Holder存储到convertView中

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_title.setText(mData.get(position).getTitle());
        holder.txt_content.setText(mData.get(position).getDescription());
        holder.txt_open.setText("提交开始时间："+mData.get(position).getOpened_at());
        holder.txt_close.setText("提交结束时间"+mData.get(position).getClosed_at());

        return convertView;
    }

    static class ViewHolder {
        TextView txt_title;
        TextView txt_content;
        TextView txt_open;
        TextView txt_close;
    }
}
