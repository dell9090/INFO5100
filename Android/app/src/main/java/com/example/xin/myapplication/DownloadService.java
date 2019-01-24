package com.example.xin.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DownloadService {

    @GET("rest/user-management/download/{id}")
    Call<ResponseBody> startDownload(
            @Path("id") int id
    );
}
