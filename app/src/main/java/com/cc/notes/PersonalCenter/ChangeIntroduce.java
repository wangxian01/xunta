package com.cc.notes.PersonalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notes.cc.notes.R;

public class ChangeIntroduce extends AppCompatActivity {
    private Toolbar mIntroduceToolbar;
    private EditText mIntroduceText;
    private Button mIntroduceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_introducel);
        mIntroduceToolbar = (Toolbar) findViewById(R.id.introduce_toolbar);
        mIntroduceText = (EditText) findViewById(R.id.introduce_text);
        mIntroduceButton = (Button) findViewById(R.id.introduce_button);
        /*
         * 设置标题为空
         * */
        mIntroduceToolbar.setTitle("");
        /*
         * 为toolbar设置返回
         * */
        setSupportActionBar(mIntroduceToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mIntroduceToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
         * 点击保存按钮
         * */
        mIntroduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChangeIntroduce.this,EditInformationActivity.class);
                intent.putExtra("mEditIntroduceString",mIntroduceText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
