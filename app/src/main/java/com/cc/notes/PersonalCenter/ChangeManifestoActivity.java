package com.cc.notes.PersonalCenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notes.cc.notes.R;


public class ChangeManifestoActivity extends AppCompatActivity {
    private Toolbar mManifestoToolbar;
    private EditText mManifestoText;
    private Button mManifestoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_manifesto);
        mManifestoToolbar = (Toolbar) findViewById(R.id.manifesto_toolbar);
        mManifestoText = (EditText) findViewById(R.id.manifesto_text);
        mManifestoButton = (Button) findViewById(R.id.manifesto_button);
        /*
         * 设置标题为空
         * */
        mManifestoToolbar.setTitle("");
        /*
         * 为toolbar设置返回
         * */
        setSupportActionBar(mManifestoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mManifestoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
        * 点击保存按钮
        * */
        mManifestoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
