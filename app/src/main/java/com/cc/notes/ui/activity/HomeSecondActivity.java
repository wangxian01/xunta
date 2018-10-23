package com.cc.notes.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.notes.model.Homeinfo;
import com.notes.cc.notes.R;

public class HomeSecondActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpaper_layout);
        TextView name,age,xingzuo,engname,hangye,xingqu,qianming,xingzuo1 ;
        ImageView image;
        int id = getIntent().getIntExtra("id",0);

        image = (ImageView) findViewById(R.id.image_top1);
        name = (TextView) findViewById(R.id.tv_name1);
        age = (TextView) findViewById(R.id.tv_age1);
        xingzuo = (TextView) findViewById(R.id.whatxingzuo);
        engname=(TextView)findViewById(R.id.jianjie) ;
        hangye=(TextView)findViewById(R.id.whathangye) ;
        xingqu=(TextView)findViewById(R.id.whatgongzuolingyu);
        qianming=(TextView)findViewById(R.id.gexingqianming);
        xingzuo1= (TextView) findViewById(R.id.tv_constellation1);

       name.setText( Homeinfo.name[id]);
       age.setText(Homeinfo.age[id]);
       xingzuo.setText(Homeinfo.xingzuo[id]);
       image.setImageResource(Homeinfo.img2[id]);
       engname.setText(Homeinfo.jianjie[id]);
       hangye.setText(Homeinfo.hangye[id]);
       xingqu.setText(Homeinfo.xingqu[id]);
       qianming.setText(Homeinfo.gexingqianming[id]);
       xingzuo1.setText(Homeinfo.xingzuo[id]);







    }
}
