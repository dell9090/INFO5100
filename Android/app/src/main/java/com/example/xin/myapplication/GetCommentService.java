package com.example.xin.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetCommentService {

    @POST("rest/user-management/comment")
    Call<CommentReceive> getit(@Body SimpleComment s);
}
