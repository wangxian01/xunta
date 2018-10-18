package com.cc.notes.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.cc.notes.model.MsgInfo;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class SocketService extends Service {

    public Socket socket;//客户端套接字

    public SocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {

            String nicheng = null;
            String shoujihao=null;

            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.187", 8888);
/*
                    //上线成功就把登陆状态设置为true
                    SharedPreferences sharedPreferencesm = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferencesm.edit();
                    editor.putBoolean("islogin",true);//连接成功便设置为登陆状态
                    //Log.e(TAG, "onCreateView: "+ sharedPreferences.getBoolean("islogin",false));

 */
                    //获取登陆时存入的用户
                    SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                   shoujihao = sharedPreferences.getString("name", "13795971992");


                    //根据手机号码查询谁上线了
                    OkHttpUtils
                            .get()
                            .url("http://192.168.1.187:8080/nicknameServlet")
                            .addParams("guanjianzi", shoujihao)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Request request, Exception e) {


                                }
                                @Override
                                public void onResponse(String response) {

                                    nicheng = response;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {

                                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                                //向服务器发送上线信息
                                                oos.writeObject(new MsgInfo(nicheng, null, shoujihao, null, "online", null, null, null));
                                                oos.flush();

                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();


                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "成功连接到服务器。。。。。。。。。 ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public SocketService getService() {
            return SocketService.this;
        }
    }
}
