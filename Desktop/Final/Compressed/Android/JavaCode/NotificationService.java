package com.example.xin.myapplication;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotificationService {

    @GET("rest/user-management/notification/{id}")
    Call<NotificationReceive> sendit(
           @Path("id") int subjectid
    );
}
