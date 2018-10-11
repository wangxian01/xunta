package com.cc.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.notes.cc.notes.R;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /*
         * 个人中心接口测试
         * */
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                OkHttpUtils
//                        .get()
//                        .url("http://" + getApplicationContext().getString(R.string.netip) + ":8080/XunTa/PersonalCenterServlet")
//                        .addParams("userid", "10086")
//                        .build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Request request, Exception e) {
//                                new AlertDialog.Builder(test.this).setMessage("网络错误！！").create().show();
//                            }
//
//                            @Override
//                            public void onResponse(String response) {
//                                if (response !=null) {
//                                    ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
//                                    Gson gson = new Gson();
//                                    userBeans = gson.fromJson(response.toString(), new TypeToken<ArrayList<UserBean>>() {
//                                    }.getType());
//                                    Log.e("测试：", "输出："+userBeans.get(0).getNickname());
//
//                                } else {
//                                    new AlertDialog.Builder(test.this).setMessage("没有搜到！！").create().show();
//                                }
//                            }
//                        });
//            }
//        });
//        thread.start();

//        Thread threads = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpUtils
//                            .get()
//                            .url("http://"+getApplicationContext().getString(R.string.netip)+":8080/XunTa/XunRegisterServlet")
//                            .addParams("userid", "李四")
//                            .build().execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        threads.start();

        /*
         * 注册接口测试
         * */
//        Thread threads = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpUtils
//                            .get()
//                            .url("http://"+getApplicationContext().getString(R.string.netip)+":8080/XunTa/XunRegisterServlet")
//                            .addParams("userid", "李四")
//                            .addParams("password", "1234567")
//                            .addParams("sex", "男")
//                            .addParams("birthday", "1998-04")
//                            .build().execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        threads.start();
    }
}
