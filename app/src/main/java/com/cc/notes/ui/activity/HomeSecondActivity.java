package com.cc.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.notes.cc.notes.R;

public class HomeSecondActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpaper_layout);
        TextView name,age,xingzuo,hangye ;
        ImageView image;
        int id = getIntent().getIntExtra("id",0);

        image = (ImageView) findViewById(R.id.image_top);
        name = (TextView) findViewById(R.id.tv_name);
        age = (TextView) findViewById(R.id.tv_age);
        xingzuo = (TextView) findViewById(R.id.tv_constellation);
        hangye = (TextView) findViewById(R.id.tv_hangye);

        name.setText(id);




    }
}
