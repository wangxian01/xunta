package com.cc.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity01 extends AppCompatActivity {

    private Button mregistered_button01;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered01);
        mregistered_button01=(Button) findViewById(R.id.registered_button01);
        mregistered_button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteredActivity01.this, RegisteredActivity02.class);
                startActivity(intent);
            }
        });

    }
}
