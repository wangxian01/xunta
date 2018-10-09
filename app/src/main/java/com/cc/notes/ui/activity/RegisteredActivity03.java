package com.cc.notes.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cc.notes.PersonalCenter.EditInformationActivity;
import com.notes.cc.notes.R;

/**
 * Created by 侯顺发 on 2018/9/6.
 */

public class RegisteredActivity03 extends AppCompatActivity {
    private EditText editText;
    private Button mregistered_button03;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered03);
        editText = findViewById(R.id.registered_age);
        mregistered_button03=(Button) findViewById(R.id.registered_button03);
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
                Intent intent = new Intent(RegisteredActivity03.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
