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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cc.notes.Service.SocketService;
import com.cc.notes.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
private TextView mLoginRegistered;
private Button mLoginButton;
private EditText mUserName;
private EditText mUserPassword;
private boolean aBoolean;
private OkHttpClient client = new OkHttpClient();
public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

private String username, password;

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {      //接收其他子线程的消息
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    new AlertDialog.Builder(LoginActivity.this).setMessage("用户登录失败，账号或密码错误").create().show();
//                    break;
//
//                case 1:
//                    //获取sharedPreferences对象
//                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
//                    //获取editor对象
//                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//                    editor.putBoolean("islogin",true);
//                    editor.putString("username",msg.obj.toString() );
//                    editor.apply();
//
//                    new AlertDialog.Builder(LoginActivity.this).setMessage( "欢迎  "+ msg.obj.toString() ).create().show();
//
//                    Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
//                    intent.putExtra("username",msg.obj.toString());
//                    startActivity(intent);
//
//                    break;
//            }
//        }
//    };

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
//                Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
//                startActivity(intent);

                if(mUserName.getText().toString().equals("13795971992")&&mUserPassword.getText().toString().equals("123")){
                    //启动连接服务
                    Intent intentservice = new Intent(LoginActivity.this, SocketService.class);
                    startService(intentservice);

                    //储存登陆用户
                    SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("islogin",true);//设置登陆状态为TRUE
                    editor.putString("name","13795971992");
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
                    startActivity(intent);

                }else if(mUserName.getText().toString().equals("18030848415")&&mUserPassword.getText().toString().equals("123")) {

                    //启动连接服务
                    Intent intentservice = new Intent(LoginActivity.this, SocketService.class);
                    startService(intentservice);

                    //储存登陆用户
                    SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("islogin", true);//设置登陆状态为TRUE
                    editor.putString("name", "18030848415");
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
                    startActivity(intent);


                }







/*                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            String restult = post("http://"+getString(R.string.netip)+":8080/XunTa/LoginServlet","");
                            Gson gson = new Gson();
                            ArrayList<UserBean> userBeans = gson.fromJson(restult,new TypeToken<ArrayList<UserBean>>() {
                            }.getType());
                            for(int i = 0  ;i < userBeans.size();i++){
                                if(String.valueOf(mUserName.getText()).equals(userBeans.get(i).getUserid().trim()) && String.valueOf(mUserPassword.getText()).equals(userBeans.get(i).getPassword().trim())){
                                    aBoolean = true;
                                    break;
                                }else {
                                    aBoolean = false;
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (aBoolean == true){
                    Intent intent = new Intent(LoginActivity.this, FirsthomeActivity.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("用户名或密码有误！")
                            .setMessage("请输入！")
                            .setPositiveButton("确定", null)
                            .show();
                }*/
            }
        });

}

    /**
     * post请求*/
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
