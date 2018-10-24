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
import com.notes.cc.notes.R;

public class HomeSecondActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpaper_layout);
        TextView name,age,xingzuo,engname,hangye,xingqu,qianming,xingzuo1 ;
        ImageView image;
        //int id = getIntent().getIntExtra("id",0);

        image = (ImageView) findViewById(R.id.image_top1);
        name = (TextView) findViewById(R.id.tv_name1);
        age = (TextView) findViewById(R.id.tv_age1);
        xingzuo = (TextView) findViewById(R.id.whatxingzuo);
        engname=(TextView)findViewById(R.id.jianjie) ;
        hangye=(TextView)findViewById(R.id.whathangye) ;
        xingqu=(TextView)findViewById(R.id.whatgongzuolingyu);
        qianming=(TextView)findViewById(R.id.gexingqianming);
        xingzuo1= (TextView) findViewById(R.id.tv_constellation1);
        Intent intent_get = getIntent();
        name.setText((String)intent_get.getStringExtra("name"));
        age.setText((String)intent_get.getStringExtra("Birthday"));
        xingzuo.setText((String)intent_get.getStringExtra("Height"));
       // Bitmap bitmap = stringToBitmap((String)intent_get.getStringExtra("img"));
        // Log.e("测试：", String.valueOf(bitmap));
        //Log.e("测试：", userBeans.get(0).getPortrait());
        //image.setImageBitmap(bitmap);
        engname.setText((String)intent_get.getStringExtra("Manifesto"));
        hangye.setText((String)intent_get.getStringExtra("Occupation"));
        xingqu.setText((String)intent_get.getStringExtra("Introduce"));
        qianming.setText((String)intent_get.getStringExtra("Site"));
        xingzuo1.setText((String)intent_get.getStringExtra("Height"));

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
