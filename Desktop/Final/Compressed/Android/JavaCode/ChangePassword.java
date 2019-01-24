package com.example.xin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword extends Activity {
    EditText old_pw ;
    EditText new_pw;
    EditText comfirm_pw;
    Button btn_comfirm;
    Button btn_cancel;
    UserInfo usr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pw);
        usr = (UserInfo) getApplicationContext();
        old_pw = (EditText) findViewById(R.id.change_pw_old);
        new_pw = (EditText) findViewById(R.id.change_pw_new);
        comfirm_pw = (EditText) findViewById(R.id.change_pw_comfirm);
        btn_comfirm = (Button) findViewById(R.id.change_pw);
        btn_cancel = (Button) findViewById(R.id.change_pw_cancel);

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = usr.getUserName();
                String txt_old_pw = old_pw.getText().toString().trim();
                String txt_new_pw = new_pw.getText().toString().trim();
                String txt_comfirm_pw = comfirm_pw.getText().toString().trim();

                if(txt_comfirm_pw.equals(txt_new_pw)) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(usr.getIp())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ChangePWService request = retrofit.create(ChangePWService.class);
                    User u = new User(usr.getUserid(), txt_new_pw, txt_old_pw);
                    Call<ChangePWReceive> call = request.sendlogin(u);


                    call.enqueue(new Callback<ChangePWReceive>() {
                        //请求成功时回调
                        @Override
                        public void onResponse(Call<ChangePWReceive> call, Response<ChangePWReceive> response) {
                            // 步骤7：处理返回的数据结果
                            if (response.body().getStatus()) {
                                Toast.makeText(ChangePassword.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(ChangePassword.this, MainPage.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ChangePassword.this, "Fail", Toast.LENGTH_LONG).show();
                            }


                        }

                        //请求失败时回调
                        @Override
                        public void onFailure(Call<ChangePWReceive> call, Throwable throwable) {
                            Toast.makeText(ChangePassword.this, "Fail!"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(ChangePassword.this, "Password not match, Please try again", Toast.LENGTH_LONG).show();
                    new_pw.setText("");
                    comfirm_pw.setText("");
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChangePassword.this,MainPage.class);
                startActivity(intent);
            }
        });
    }
}
