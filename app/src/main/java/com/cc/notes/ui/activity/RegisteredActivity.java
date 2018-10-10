package com.cc.notes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity extends AppCompatActivity {

    private Button mregistered_button;
    private TextView registered_username;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        registered_username=(TextView)findViewById(R.id.registered_username);
        mregistered_button=(Button) findViewById(R.id.registered_button);

        mregistered_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putString("username",registered_username.getText().toString());//参数传值
                Intent intent = new Intent(RegisteredActivity.this, RegisteredActivity01.class);
                intent.putExtra("username",registered_username.getText().toString());

                startActivity(intent);
            }
        });

    }
}
