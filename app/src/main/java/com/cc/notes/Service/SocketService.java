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
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.187", 8888);
                    socket.setSoTimeout(10000);

                    //上线成功就把登陆状态设置为true
                    SharedPreferences sharedPreferencesm = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferencesm.edit();
                    editor.putBoolean("islogin",true);//连接成功便设置为登陆状态
                    //Log.e(TAG, "onCreateView: "+ sharedPreferences.getBoolean("islogin",false));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.print("有人连接了。。。。。");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public  class MyBinder extends Binder {
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
