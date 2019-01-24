package com.example.xin.myapplication;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ChangePWService {

    @PUT("rest/user-management/users")
    Call<ChangePWReceive> sendlogin(@Body User user);
}
