package com.cc.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity02 extends AppCompatActivity {
    private  Boolean boolean_man = false,boolean_woman=false;
    private ImageView seximag_1,seximag_2;
    private Button mregistered_button02;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered02);
        mregistered_button02=(Button) findViewById(R.id.registered_button02);
        seximag_1 = findViewById(R.id.registered_man);
        seximag_2=findViewById(R.id.registered_woman);



        seximag_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolean_woman==false && boolean_man == false){
                    seximag_2.setImageResource(R.drawable.woman_white);
                    boolean_woman=true;
                }else{
                    seximag_2.setImageResource(R.drawable.woman);
                    boolean_woman=false;
                }
            }
        });
        seximag_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boolean_man == false && boolean_woman==false){
                    seximag_1.setImageResource(R.drawable.man_white);
                    boolean_man = true;
                }else{
                    seximag_1.setImageResource(R.drawable.man);
                    boolean_man = false;
                }
            }
        });


        mregistered_button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteredActivity02.this, RegisteredActivity03.class);
                startActivity(intent);
            }
        });

    }
}
