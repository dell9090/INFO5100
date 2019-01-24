package com.example.xin.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentAdapter extends BaseAdapter {

    private LinkedList<Comment> mData;
    private Context mContext;
    private UserInfo usr;

    public CommentAdapter(LinkedList<Comment> mData, Context mContext, UserInfo usr) {
        this.mData = mData;
        this.mContext = mContext;
        this.usr = usr;
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

        if(convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);
            holder = new ViewHolder();
            holder.txt_comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            holder.btn_comment_agree = (Button) convertView.findViewById(R.id.comment_agree);
            convertView.setTag(holder);   //将Holder存储到convertView中

        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_comment_content.setText(mData.get(position).getContent());
        holder.btn_comment_agree.setText(Integer.toString(mData.get(position).getAgreecount()));
        int situation = mData.get(position).getIfagree();
        if(situation == 1){
            holder.btn_comment_agree.setBackgroundColor(Color.parseColor("#6495ED"));
        }
        else{
            holder.btn_comment_agree.setBackgroundColor(Color.parseColor("#D4D4D4"));
        }

        holder.btn_comment_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //点赞
                int situation = mData.get(position).getIfagree();
                int count = mData.get(position).getAgreecount();
                Button button = (Button)v.findViewById(v.getId());

                if(situation==1 && count>0){
                    count--;
                    button.setBackgroundColor(Color.parseColor("#D4D4D4"));
                    mData.get(position).setIfagree(0);
                }else if(situation==0){
                    count++;
                    button.setBackgroundColor(Color.parseColor("#6495ED"));
                    mData.get(position).setIfagree(1);
                }

                mData.get(position).setAgreecount(count);
                button.setText(Integer.toString(count));

                sendmessage(mData.get(position));
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView txt_comment_content;
        Button btn_comment_agree;
    }

    public void sendmessage(Comment c){

        Log.d("test","ip: "+usr.getIp());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usr.getIp()) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final boolean result = false;
        ModifyCommentService request = retrofit.create(ModifyCommentService.class);

        Call<CommentReceive> call = request.send(c);

        call.enqueue(new Callback<CommentReceive>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<CommentReceive> call, Response<CommentReceive> response) {

                if (response.body().getCount() != 0) {
                    Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "fail", Toast.LENGTH_LONG).show();
                }

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<CommentReceive> call, Throwable throwable) {
                Toast.makeText(mContext, "fail!"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
