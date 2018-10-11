package com.cc.notes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity01 extends AppCompatActivity {
    private TextView registered_password,registered_confirm,registered_username;
    private Button mregistered_button01;
    private String string;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered01);
        mregistered_button01=(Button) findViewById(R.id.registered_button01);
        registered_password=(TextView)findViewById(R.id.registered_password);
        registered_confirm=(TextView)findViewById(R.id.registered_confirm);
        registered_username=(TextView)findViewById(R.id.registered_username);
        Intent intent_1 = getIntent();
        string = intent_1.getStringExtra("username");




       // Log.e("测试","第一页的参数传递"+intent_1.getStringExtra("username"));
        mregistered_button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registered_password.getText()==null || registered_password.getText().length()==0||registered_password.getText().equals("")&&registered_password!=registered_confirm){
                    Toast.makeText(getApplicationContext(),"密码不能为空", Toast.LENGTH_SHORT).show();

                }else if (!registered_password.getText().toString().equals(registered_confirm.getText().toString())){
                    Toast.makeText(getApplication(),"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(RegisteredActivity01.this, RegisteredActivity02.class);
                    intent.putExtra("password",registered_password.getText().toString());
                    intent.putExtra("username",string);
                    startActivity(intent);
                }
            }
        });

    }
}
