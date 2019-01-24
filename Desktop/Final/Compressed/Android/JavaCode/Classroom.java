package com.example.xin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Classroom extends Activity implements View.OnClickListener, OnPageChangeListener, OnDrawListener, OnLoadCompleteListener {

    private String info_type= "Classroom";
    private DanmakuView mDanmakuView;
    private Button switcherBtn;
    private Button sendBtn;
    private EditText textEditText;
    private PDFView pdfView;
    private CommentAdapter commentAdapter;
    private ListView commentList;
    private List<Comment> mComment = null;
    private List<Comment> showComment= null;
    private Map<String,File> mFilelist = null;
    private QMUIPullRefreshLayout qmuicomment;
    private QMUITopBar mTopBar;
    private Context mContext;
    private UserInfo usr;
    private int global_page;
    private int global_pdfid;
    private String name;
    private int user_id;
    private int subjectid;
    private boolean danma_stat=true;
    private String[] items = new String[100];
    private int[] items_id = new int[100];
    private LinkedList<Courseware> coursewareLinkedList;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom);

        usr = (UserInfo) getApplicationContext();
        name = usr.getUserName();
        subjectid = usr.getSubjectid();
        user_id = usr.getUserid();

        getCourseware();

        global_page=1;
        global_pdfid=0;

        mContext = Classroom.this;
        mComment = new LinkedList<Comment>();
        mFilelist = new HashMap<String, File>();
        coursewareLinkedList = new LinkedList<Courseware>();

        commentList=(ListView)findViewById(R.id.comment_list);

        mDanmakuView = (DanmakuView) findViewById(R.id.danmakuView);
        switcherBtn = (Button) findViewById(R.id.switcher);
        sendBtn = (Button) findViewById(R.id.send);
        textEditText = (EditText) findViewById(R.id.text);
        qmuicomment = (QMUIPullRefreshLayout) findViewById(R.id.pull_to_refresh);
        mTopBar = (QMUITopBar) findViewById(R.id.topbar_classroom);

        mTopBar.addRightTextButton(R.string.topbar_rightbtn_classroom,R.id.topbar_rightbtn_classroom_id)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        List<IDanmakuItem> list = initItems();
        Collections.shuffle(list);
        mDanmakuView.addItem(list, true);

        switcherBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);

        qmuicomment.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                refreshComment();
                qmuicomment.finishRefresh();
            }
        });

        pdfView = (PDFView) findViewById( R.id.pdfView );
    }

    private void showSingleChoiceDialog() {

        final int checkedIndex = getCheckIndex(subjectid);

        new QMUIDialog.CheckableDialogBuilder(Classroom.this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {

                        global_pdfid=items_id[which];
                        global_page=1;

                        loadCheckIndex(subjectid,which);

                        Toast.makeText(mContext, items[which]+" id: "+items_id[which], Toast.LENGTH_SHORT).show();

                        showLoading();

                        Thread downloadrunable = new Thread(){
                            public void run(){
                                super.run();
                                downloadCourseware(items[which]);

                            }

                        };

                        downloadrunable.start();

                        dialog.dismiss();

                        refreshComment();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void displayFromAssets(String name, int defaultpage ) {

        File file = mFilelist.get(name);

        pdfView.fromFile(file)   //设置pdf文件地址
                .defaultPage(defaultpage)         //设置默认显示第1页
                .onPageChange(this)     //设置翻页监听
                .onLoad(this)           //设置加载监听
                .onDraw(this)            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical( false )  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻页
//                 .pages()  //把 5 过滤掉
                .load();
    }
    /**
     * 获取打开网络的pdf文件
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1( String fileUrl ,String fileName) {
        pdfView.fileFromLocalStorage(this,this,this,fileUrl,fileName);   //设置pdf文件地址

    }

    /**
     * 翻页回调
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
        global_page = page;
        Toast.makeText( Classroom.this , "page= " + global_page +
                " pageCount= " + pageCount , Toast.LENGTH_SHORT).show();

        refreshComment();

    }

    /**
     * 加载完成回调
     * @param nbPages  总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
        Toast.makeText( Classroom.this ,  "加载完成" + nbPages  , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    private List<IDanmakuItem> initItems() {
        List<IDanmakuItem> list = new ArrayList<>();
        //ADD previous danmaku
        return list;
    }

    private List<IDanmakuItem> emptyItems() {
        List<IDanmakuItem> list = new ArrayList<>();
        return list;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mDanmakuView.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDanmakuView.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
        mDanmakuView.clear();

        Log.d("test","quit classroom");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switcher:
                if (!danma_stat) {//click show
                    switcherBtn.setText(R.string.hide);

                    for(Comment i : mComment){
                        if(i.getPage()==global_page && i.getPost_id() == global_pdfid){
                            showComment.add(i);
                            IDanmakuItem item = new DanmakuItem(this, new SpannableString(i.getContent()), mDanmakuView.getWidth(),0,R.color.my_item_color,0,1);
                            mDanmakuView.addItemToHead(item);
                        }
                    }

                } else {//click hide
                    switcherBtn.setText(R.string.show);
                    List<IDanmakuItem> list = emptyItems();

                }
                danma_stat = !danma_stat;
                break;
            case R.id.send:
                String input = textEditText.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(Classroom.this, R.string.empty_prompt, Toast.LENGTH_SHORT).show();
                } else {

                    if(danma_stat) {
                        IDanmakuItem item = new DanmakuItem(this, new SpannableString(input), mDanmakuView.getWidth(),0,R.color.my_item_color,0,1);
                        mDanmakuView.addItemToHead(item);
                    }

                    Comment temp = new Comment();
                    temp.setContent(input);
                    temp.setUsrid(usr.getUserid());
                    temp.setSubjectid(subjectid);
                    temp.setPost_type(0);
                    temp.setPage(global_page);
                    temp.setPost_id(global_pdfid);
                    mComment.add(temp);

                    Toast.makeText( Classroom.this , "Text:"+input+" ,Page:"+global_page, Toast.LENGTH_SHORT).show();


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(usr.getIp())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    SendCommentService request = retrofit.create(SendCommentService.class);

                    Call<CommentReceive> call = request.sendit(temp);

                    call.enqueue(new Callback<CommentReceive>() {
                        //请求成功时回调
                        @Override
                        public void onResponse(Call<CommentReceive> call, Response<CommentReceive> response) {

                            if (response.body().getCount() != 0) {
                                Toast.makeText(Classroom.this, "send success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Classroom.this, "send fail", Toast.LENGTH_LONG).show();
                            }

                        }
                        //请求失败时回调
                        @Override
                        public void onFailure(Call<CommentReceive> call, Throwable throwable) {
                            Toast.makeText(Classroom.this, "send fail!" +
                                    throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    refreshComment();
                }
                textEditText.setText("");
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void refreshComment(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usr.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCommentService request = retrofit.create(GetCommentService.class);
        SimpleComment s = new SimpleComment(global_pdfid, global_page, subjectid);
        Call<CommentReceive> call = request.getit(s);

        call.enqueue(new Callback<CommentReceive>() {

            @Override
            public void onResponse(Call<CommentReceive> call, Response<CommentReceive> response) {

                Toast.makeText(Classroom.this, "refresh success", Toast.LENGTH_SHORT).show();

                int count = response.body().getCount();
                mComment = response.body().getCommentlist();
            }

            @Override
            public void onFailure(Call<CommentReceive> call, Throwable throwable) {
                Toast.makeText(Classroom.this, "refresh fail!", Toast.LENGTH_SHORT).show();
            }
        });

        showComment = new LinkedList<Comment>();

        for(Comment i : mComment){
            if(i.getPage()==global_page && i.getPost_id() == global_pdfid){
                showComment.add(i);
                if(danma_stat){
                    int color = R.color.other_item_color;

                    if(i.getUsrid()==usr.getUserid()){
                        color = R.color.my_item_color;
                    }
                    IDanmakuItem item = new DanmakuItem(Classroom.this, new SpannableString(i.getContent()), mDanmakuView.getWidth(),0,color,0,1);
                    mDanmakuView.addItemToHead(item);
                }

            }
        }

        commentAdapter = new CommentAdapter((LinkedList<Comment>)showComment,mContext,usr);
        commentList.setAdapter(commentAdapter);
    }

    public void getCourseware(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(usr.getIp()) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        GetCoursewareService request = retrofit1.create(GetCoursewareService.class);

        Call<CoursewareReceive> call = request.sendid(subjectid);

        call.enqueue(new Callback<CoursewareReceive>() {
            //请求成功时回调
            @Override

            public void onResponse(Call<CoursewareReceive> call, Response<CoursewareReceive> response) {

                coursewareLinkedList = response.body().getCoursewareLinkedList();
                int i = 0;
                for(Courseware c : coursewareLinkedList){
                    items[i]=c.getName();
                    items_id[i]=c.getId();
                    i++;
                }

            }
            //请求失败时回调
            @Override
            public void onFailure(Call<CoursewareReceive> call, Throwable throwable) {
                Toast.makeText(Classroom.this, "刷新失败! throwable.getMessage()", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void downloadCourseware(final String filename){
        File files = mFilelist.get(filename);
        if(files==null || files.length()==0){//mFilelist 不存在
            Retrofit retrofit1 = new Retrofit.Builder()
                    .baseUrl(usr.getIp()) // 设置 网络请求 Url
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                    .build();


            DownloadService downloadService = retrofit1.create(DownloadService.class);

            Call<ResponseBody> call = downloadService.startDownload(global_pdfid);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(),filename);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("test", "error");
                }
            });
        }else{
            hideLoading();//隐藏加载框
            displayFromAssets(filename,1);
        }



    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            // todo change the file location/name according to your needs
            File file = new File(getExternalFilesDir(null) + File.separator + filename+".pdf");

            mFilelist.put(filename,file);


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
                        displayFromAssets(filename,1);
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

    /**
     * 监听回退键
     */
    @Override
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

    public void loadCheckIndex(int id, int num) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "class_setting_"+ Integer.toString(id));
            helper.putValues(new SharedPreferencesUtils.ContentValue("index", num));
    }

    public int getCheckIndex(int id) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "class_setting_"+ Integer.toString(id));
        int index = helper.getInt("index");
        return index;
    }

}