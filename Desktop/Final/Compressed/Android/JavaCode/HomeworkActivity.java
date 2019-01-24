package com.example.xin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeworkActivity extends Activity {

    private String info_type = "Homework";
    private int subjectid;
    private UserInfo usr;
    private int user_id;
    private String[] items = new String[100];
    private String[] attachments = new String[100];
    private Context mContext;
    private QMUITopBar mTopBar;
    private String filename;
    private LinkedList<Homework> homeworkLinkedList;
    private List<HomeworkContent> mHomeworkContent;
    private ListView mContent;
    private HomeworkContentAdapter homeworkApater;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework);

        usr = (UserInfo) getApplicationContext();
        subjectid = usr.getSubjectid();
        user_id =usr.getUserid();

        mHomeworkContent = new LinkedList<HomeworkContent>();

        mContent = (ListView) findViewById(R.id.homework_list);

        mContext = HomeworkActivity.this;

        getHomework();

        mTopBar = (QMUITopBar) findViewById(R.id.topbar_homework);

        mTopBar.addRightTextButton(R.string.topbar_rightbtn_homework,R.id.topbar_rightbtn_homework_id)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSingleChoiceDialog();
                    }
                });
    }

    private void showSingleChoiceDialog() {// download attachment
        final int checkedIndex = getCheckIndex(subjectid);

        new QMUIDialog.CheckableDialogBuilder(HomeworkActivity.this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {

                        loadCheckIndex(subjectid,which);
                        //download
                        Log.d("test","attachid: "+attachments[which]);

                        if(attachments[which]!=null)
                        {
                            showLoading();

                            Thread downloadrunable = new Thread(){
                                public void run(){
                                    super.run();
                                    filename = items[which];
                                    //downloadCourseware(attachments[which]);
                                }

                            };

                            downloadrunable.start();

                            dialog.dismiss();
                        }

                        Toast.makeText(mContext, items[which], Toast.LENGTH_SHORT).show();

                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void loadCheckIndex(int id, int num) {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "homework_setting_"+ Integer.toString(id));
        helper.putValues(new SharedPreferencesUtils.ContentValue("index", num));
    }


    public int getCheckIndex(int id) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "homework_setting_"+ Integer.toString(id));
        int index = helper.getInt("index");
        return index;
    }

    public void getHomework(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usr.getIp()) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetHomeworkService request = retrofit.create(GetHomeworkService.class);

        Call<HomeworkReceive> call = request.getit(subjectid);

        call.enqueue(new Callback<HomeworkReceive>() {
            //请求成功时回调
            @Override

            public void onResponse(Call<HomeworkReceive> call, Response<HomeworkReceive> response) {

                int count = response.body().getCount();
                homeworkLinkedList = response.body().getHomeworkList();
                getHomeworkList();
                getHomeworkContent();
            }
            //请求失败时回调
            @Override
            public void onFailure(Call<HomeworkReceive> call, Throwable throwable) {
                Toast.makeText(HomeworkActivity.this, "刷新失败! throwable.getMessage()", Toast.LENGTH_SHORT).show();
            }


        });

    }

    public void getHomeworkList(){
        int i = 0;
        for (Homework h : homeworkLinkedList) {
            items[i]=h.getTitle();
            attachments[i]=Integer.toString(h.getAttachment_id());
            i++;
        }
    }

    public void getHomeworkContent(){
        for (Homework h : homeworkLinkedList) {
            HomeworkContent temp = new HomeworkContent(h.getTitle(), h.getDescription(),
                    h.getOpen_at(), h.getClose_at());

            mHomeworkContent.add(temp);
        }

        homeworkApater = new HomeworkContentAdapter((LinkedList<HomeworkContent>) mHomeworkContent, mContext);
        mContent.setAdapter(homeworkApater);

    }

    public void downloadCourseware(final String id){

            Retrofit retrofit1 = new Retrofit.Builder()
                    .baseUrl(usr.getIp()) // 设置 网络请求 Url
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                    .build();


            DownloadHomeworkService downloadService = retrofit1.create(DownloadHomeworkService.class);

            Call<ResponseBody> call = downloadService.startDownload(id);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("test", "error");
                }
            });

    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
//            String cacheDir = Environment.getDownloadCacheDirectory().toString();
//            System.out.println("Environment.getDownloadCacheDirectory()=:" + cacheDir);
            Log.d("test","Environment.getDataDirectory():"+Environment.getDataDirectory());
            Log.d("test","getExternalFilesDir(null):"+getExternalFilesDir(null));
            Log.d("test","Environment.getDownloadCacheDirectory():"+Environment.getDownloadCacheDirectory());
            File file = new File(Environment.getDownloadCacheDirectory() + File.separator + filename+".pdf");
            Log.d("test","real path:"+file.getPath());
            //File file = new File(getExternalFilesDir(null) + File.separator +filename+".pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("test", "file download: " + fileSizeDownloaded + " of " + fileSize);

                    if(fileSizeDownloaded == fileSize){ // finish load
                        hideLoading();//隐藏加载框
                    }
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }
        mLoadingDialog.show();
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }

    public void onBackPressed() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            } else {
                finish();
            }
        } else {
            finish();
        }

    }
}
