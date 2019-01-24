package com.example.xin.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendCommentService {

    @POST("rest/user-management/comment/send")
    Call<CommentReceive> sendit(
            @Body Comment c
    );
}
