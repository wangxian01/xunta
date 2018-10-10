package com.cc.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity02 extends AppCompatActivity {
    private  Boolean boolean_man = false,boolean_woman=false;
    private ImageView seximag_1,seximag_2;
    private String sex;
    private TextView registered_password,registered_username;
    private Button mregistered_button02,registered_man,registered_woman;
    private String username,password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered02);
        mregistered_button02=(Button) findViewById(R.id.registered_button02);
        seximag_1 = findViewById(R.id.registered_man);
        seximag_2=findViewById(R.id.registered_woman);
        registered_password=(TextView)findViewById(R.id.registered_password);
        registered_username=(TextView)findViewById(R.id.registered_username);
        Intent intent_2 = getIntent();
        username = intent_2.getStringExtra("username");
        password = intent_2.getStringExtra("password");


        seximag_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolean_woman==false && boolean_man == false){
                    seximag_2.setImageResource(R.drawable.woman_white);
                    sex = "女";
                    Log.e("性别", "sss"+sex );
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
                    sex = "男";
                    Log.e("性别", "sss"+sex );
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
                if(sex.equals("")){
                    Toast.makeText(getApplication(),"性别不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(RegisteredActivity02.this, RegisteredActivity03.class);
                    intent.putExtra("sex",sex);
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }

            }
        });

    }
}
