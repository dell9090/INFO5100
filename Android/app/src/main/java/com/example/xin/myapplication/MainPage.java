package com.example.xin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPage extends Activity {

    private String info_type = "APP";
    private DrawerLayout drawer_layout;
    private ListView menu;
    private TextView welcome;
    private String[] str1={"Password","Homework","Log out"};
    private String[] str2={"Password","Homework","Statics","Log out"};
    private Button button;
    private UserInfo usr;
    private List<Notification> mNotification;
    private ListView lvNotification;
    private Context mContext;
    private QMUITopBar mTopBar;
    private NotificationAdapter mNotifAdapter;
    private int courseId;
    private int user_id;
    private QMUIPullRefreshLayout qmuinotification;
    private String[] items = new String[100];
    private int[] ids= new int[100];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mContext = MainPage.this;

        usr = (UserInfo) getApplicationContext();

        user_id = usr.getUserid();
        welcome = (TextView) findViewById(R.id.menu_content);
        String temp="Welcome Student, "+usr.getUserName()+"!"+"  Course："+getCheckName(user_id);

        courseId = getCheckId(user_id);
        usr.setSubjectid(courseId);
        welcome.setText(temp);


        qmuinotification = (QMUIPullRefreshLayout) findViewById(R.id.refresh_notification);


        lvNotification = (ListView)findViewById(R.id.notification_list);
        mNotification = new LinkedList<Notification>();
        mTopBar = (QMUITopBar) findViewById(R.id.topbar_mainpage);
        getNotification();
        getSubjectlist();

        mTopBar.addRightTextButton(R.string.topbar_rightbtn_mainpage,R.id.topbar_rightbtn_mainpage_id)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSingleChoiceDialog();
                    }
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_expandable_list_item_1,str1);

        menu = (ListView) findViewById(R.id.menu_slider);
        menu.setAdapter(adapter);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0: //修改密码
                        intent.setClass(MainPage.this,ChangePassword.class);
                        break;

                    case 1: //查看作业
                        intent.setClass(MainPage.this,HomeworkActivity.class);
                        break;

                    case 2: //注销用户
                        intent.setClass(MainPage.this,LoginActivity.class);
                        break;
                    }
                    startActivity(intent);
                }
            });



        qmuinotification.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                getNotification();
                qmuinotification.finishRefresh();
            }
        });

        button = (Button)findViewById(R.id.btn_enter_classroom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this,Classroom.class));
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();

        Log.d("test","quit classroom");
    }
    
    private void showSingleChoiceDialog() {
        final int checkedIndex = getCheckIndex(user_id);

        new QMUIDialog.CheckableDialogBuilder(mContext)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {

                        loadCheckIndex(user_id,which);
                        usr.setSubjectid(ids[which]);
                        usr.setSubjectname(items[which]);
                        courseId = getCheckId(user_id);

                        String temp = "Welcome Student, " + usr.getUserName() +
                                "!" + "  Course：" + usr.getSubjectname();
                        welcome.setText(temp);
                        Toast.makeText(mContext, items[which] + "id: " +
                                ids[which], Toast.LENGTH_SHORT).show();

                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    public void loadCheckIndex(int id, int num) {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "subject_setting_"+ Integer.toString(id));
        helper.putValues(new SharedPreferencesUtils.ContentValue("index", num));
        helper.putValues(new SharedPreferencesUtils.ContentValue("sub_id", ids[num]));
        helper.putValues(new SharedPreferencesUtils.ContentValue("sub_name", items[num]));
    }

    public int getCheckIndex(int id) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "subject_setting_"+ Integer.toString(id));
        int index = helper.getInt("index");
        return index;
    }

    public int getCheckId(int id) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "subject_setting_"+ Integer.toString(id));
        int sub_id = helper.getInt("sub_id");
        return sub_id;
    }

    public String getCheckName(int id) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "subject_setting_"+ Integer.toString(id));
        String sub_name = helper.getString("sub_name");
        return sub_name;
    }

    
    public void getNotification(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usr.getIp()) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        NotificationService request = retrofit.create(NotificationService.class);

        Call<NotificationReceive> call = request.sendit(courseId);

        call.enqueue(new Callback<NotificationReceive>() {
            //请求成功时回调
            @Override

            public void onResponse(Call<NotificationReceive> call, Response<NotificationReceive> response) {

                int i=0;

                if(response.body().getCount()!=0) {
                    mNotification = response.body().getNotificationList();
                    mNotifAdapter = new NotificationAdapter((LinkedList<Notification>) mNotification, mContext);
                    lvNotification.setAdapter(mNotifAdapter);
                }

                Toast.makeText(mContext, "Refresh Success！", Toast.LENGTH_SHORT).show();
                //Log.d("test","date change:"+mNotification.get(0).getDate());

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<NotificationReceive> call, Throwable throwable) {
                Toast.makeText(MainPage.this, "Refresh Fail!", Toast.LENGTH_SHORT).show();
            }


        });

    }

    public void getSubjectlist(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usr.getIp()) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetSubjctService request = retrofit.create(GetSubjctService.class);

        Call<SubjectReceive> call = request.getit(user_id);

        call.enqueue(new Callback<SubjectReceive>() {
            //请求成功时回调
            @Override

            public void onResponse(Call<SubjectReceive> call, Response<SubjectReceive> response) {

                if(response.body().getCount()!=0) {
                    items = response.body().getNamelist();
                    ids = response.body().getSubjectlist();
                }
                Toast.makeText(mContext, "Refresh Success！", Toast.LENGTH_SHORT).show();
                //Log.d("test","date change:"+mNotification.get(0).getDate());

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<SubjectReceive> call, Throwable throwable) {
                Toast.makeText(MainPage.this, "Refresh Fail!", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
