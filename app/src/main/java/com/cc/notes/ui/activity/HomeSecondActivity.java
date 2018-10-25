package com.cc.notes.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.notes.adapter.SerializableMap;
import com.cc.notes.model.Homeinfo;
import com.cc.notes.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

public class HomeSecondActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpaper_layout);
        final TextView name,age,xingzuo,engname,hangye,xingqu,qianming,xingzuo1;
        final ImageView image;


        image = (ImageView) findViewById(R.id.image_top1);
        name = (TextView) findViewById(R.id.tv_name1);
        age = (TextView) findViewById(R.id.tv_age1);
        xingzuo = (TextView) findViewById(R.id.whatxingzuo);
        engname=(TextView)findViewById(R.id.jianjie) ;
        hangye=(TextView)findViewById(R.id.whathangye) ;
        xingqu=(TextView)findViewById(R.id.whatgongzuolingyu);
        qianming=(TextView)findViewById(R.id.gexingqianming);
        xingzuo1= (TextView) findViewById(R.id.tv_constellation1);
        final Intent intent_get = getIntent();
        //int id = getIntent().getIntExtra("id",0);
//        byte[] b = (byte[]) intent_get.getStringExtra("img");
//        byte[] b = intent_get.getExtras().getByteArray("img");
//        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
//        image.setImageBitmap(bmp);
        Log.e("测试id：", (String)intent_get.getStringExtra("id"));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .get()
                        .url("http://" + getString(R.string.netip) + ":8080/XunTa/PersonalCenterServlet")
                        .addParams("userid", (String)intent_get.getStringExtra("id")) //模拟个人中心id为10086
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                // new AlertDialog.Builder(getActivity()).setMessage("网络错误！！").create().show();
                            }
                            @Override
                            public void onResponse(String response) {
                                if (response !=null) {
                                    ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
                                    Gson gson = new Gson();
                                    userBeans = gson.fromJson(response.toString(), new TypeToken<ArrayList<UserBean>>() {
                                    }.getType());
                                    Bitmap bitmap = stringToBitmap(userBeans.get(0).getPortrait());
                                    // Log.e("测试：", String.valueOf(bitmap));
                                    //Log.e("测试：", userBeans.get(0).getPortrait());
                                    image.setImageBitmap(bitmap);
                                    name.setText(userBeans.get(0).getNickname());
                                    age.setText(userBeans.get(0).getBirthday()+" ♀");
                                    xingzuo.setText(userBeans.get(0).getHeight()+" cm");
                                    engname.setText(userBeans.get(0).getManifesto());
                                    hangye.setText(userBeans.get(0).getOccupation());
                                    qianming.setText(userBeans.get(0).getIntroduce());
                                    xingqu.setText("看书");
                                    xingzuo1.setText(userBeans.get(0).getHeight()+" cm");

                                } else {

                                }
                            }
                        });
            }
        });
        thread.start();


//        name.setText((String)intent_get.getStringExtra("name"));
//        age.setText((String)intent_get.getStringExtra("Birthday"));
//        xingzuo.setText((String)intent_get.getStringExtra("Height"));
//        engname.setText((String)intent_get.getStringExtra("Manifesto"));
//        hangye.setText((String)intent_get.getStringExtra("Occupation"));
//        xingqu.setText("看书");
//
//        xingzuo1.setText((String)intent_get.getStringExtra("Height"));
        // Bitmap bitmap = stringToBitmap((String)intent_get.getStringExtra("img"));
        // Log.e("测试：", String.valueOf(bitmap));
        //Log.e("测试：", userBeans.get(0).getPortrait());
        //image.setImageBitmap(bitmap);
        // xingqu.setText((String)intent_get.getStringExtra("Introduce"));
         //qianming.setText((String)intent_get.getStringExtra("Site"));
    }

    /**
     * 将字符串转换成Bitmap类型
     * */
    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.URL_SAFE);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
