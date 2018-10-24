package com.cc.notes.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private CheckBox rememberPass;
    private EditText mUserPassword;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
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
        rememberPass=(CheckBox)findViewById(R.id.remember_password);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemenber=pref.getBoolean("remember_password",false);
        if(isRemenber){
            //将账号和密码都设置到文本中
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            mUserName.setText(account);
            mUserPassword.setText(password);
            rememberPass.setChecked(true);
            boolean isRemember=pref.getBoolean("remember_password",false);
            if(isRemenber){
                //将账号和密码都设置到文本中
                String  user_name=pref.getString("user_name","");
                String user_password=pref.getString("user_password","");
                mUserName.setText(account);
                mUserPassword.setText(password);
                rememberPass.setChecked(true);

            }
        }

        //blog.csdn.net/zhang_sn001/article/details/70058802

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

//        SharedPreferences sharedPreferences=getSharedPreferences("jzd",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("password", password);
//        editor.commit();//提交修改
        mLoginRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String account=mUserName.getText().toString();
                String password=mUserPassword.getText().toString();
                //如果账号是admin且密码是123456就认为登陆成功
                if(account.equals(account)&&password.equals(password)){
                    editor=pref.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent=new Intent(LoginActivity.this,FirsthomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });}

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
