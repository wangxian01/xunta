package com.cc.notes.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.notes.PersonalCenter.EditInformationActivity;
import com.notes.cc.notes.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity03 extends AppCompatActivity {
    private EditText editText;
    private Button mregistered_button03;
    private String sex,username,password;
    private TextView registered_password,registered_username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered03);
        editText = findViewById(R.id.registered_age);
        mregistered_button03=(Button) findViewById(R.id.registered_button03);
        Intent intent_3 = getIntent();
        username = intent_3.getStringExtra("username");
        password = intent_3.getStringExtra("password");
        sex = intent_3.getStringExtra("sex");
        Log.e("前数据", username+password+sex);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisteredActivity03.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                    }
                },2000,1,2).show();
            }
        });

        mregistered_button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText()==null || editText.getText().length()==0||editText.getText().equals("")){
                    Toast.makeText(getApplication(),"出生日期不能为空",Toast.LENGTH_SHORT).show();
                }else{
                            /*
         * 注册接口测试
         * */
        Thread threads = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpUtils
                            .get()
                            .url("http://"+getApplicationContext().getString(R.string.netip)+":8080/XunTa/XunRegisterServlet")
                            .addParams("userid", username)
                            .addParams("password", password)
                            .addParams("sex", sex)
                            .addParams("birthday", editText.getText().toString())
                            .build().execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        threads.start();
                    Intent intent = new Intent(RegisteredActivity03.this, LoginActivity.class);
                    intent.putExtra("user",username);
                    startActivity(intent);
                }

            }
        });

}

    }