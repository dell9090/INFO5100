package com.example.xin.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetHomeworkService  {

    @GET("rest/user-management/homework/{id}")
    Call<HomeworkReceive> getit(
            @Path("id") int subjectid
    );
}
