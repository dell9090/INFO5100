package com.example.xin.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DownloadHomeworkService {
    @FormUrlEncoded
    @POST("/laravel/public/index.php/api/downloadhomework")
    Call<ResponseBody> startDownload(
            @Field("id") String id
    );
}
