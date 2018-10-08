package com.cc.notes.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.notes.cc.notes.R;


public class LoginActivity extends AppCompatActivity {
private TextView mLoginRegistered;
private Button mLoginButton;
private EditText mUserName;
private EditText mUserPassword;

private String username, password;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {      //接收其他子线程的消息
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new AlertDialog.Builder(LoginActivity.this).setMessage("用户登录失败，账号或密码错误").create().show();
                    break;

                case 1:
                    //获取sharedPreferences对象
                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    //获取editor对象
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putBoolean("islogin",true);
                    editor.putString("username",msg.obj.toString() );
                    editor.apply();

                    new AlertDialog.Builder(LoginActivity.this).setMessage( "欢迎  "+ msg.obj.toString() ).create().show();

                    Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
                    intent.putExtra("username",msg.obj.toString());
                    startActivity(intent);

                    break;
            }
        }
    };

    @SuppressLint("MissingSuperCall")
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mLoginRegistered = (TextView) findViewById(R.id.login_registered);
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPassword = (EditText) findViewById(R.id.user_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            username = mUserName.getText().toString();
//            password = mUserPassword.getText().toString();
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    OkHttpUtils
//                            .get()
//                            .url("http://"+getString(R.string.netip)+":8080/AndroidServers/xuntaServlet")
//                            .addParams("username", username)
//                            .addParams("password", password)
//                            .build()
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//
//                                }
//
//                                @Override
//                                public void onResponse(String response, int id) {
//
//                                }
//
//
//                                public void onError(DownloadManager.Request request, Exception e) {
//                                    Log.e(TAG, "网络错误");
//                                }
//
//
//                                public void onResponse(String response) {
//
//                                    if (response!= null) {
//                                        try {
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            String name = jsonObject.getString("username");
//                                            Message msg = new Message();
//                                            msg.what = 1;
//                                            msg.obj = name;
//                                            mHandler.sendMessage(msg);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    } else {
//                                        Message msg = new Message();
//                                        msg.what = 0;
//                                        mHandler.sendMessage(msg);
//                                    }
//                                }
//                            });
//
//                }
//            });
//            thread.start();
//        }
//    });
        mLoginRegistered.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
                                                startActivity(intent);
                                            }
                                        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
                startActivity(intent);
            }
        });

}
}
