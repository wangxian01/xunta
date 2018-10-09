package com.cc.notes.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.mrzk.specialprolibrary.SpecialProgressBarView;
import com.notes.cc.notes.R;

import java.lang.ref.WeakReference;

public class IntimacyActivity extends AppCompatActivity {

    private static final int WHAT = 1;
    int num = 0;
    SpecialProgressBarView ls;
    MyHandler handler = new MyHandler(this);
    class MyHandler extends Handler{
        WeakReference<Activity> weakReference;
        public MyHandler(Activity activity){
            weakReference = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get()!=null){
                switch (msg.what){
                    case WHAT:
                        num++;
                        if (num<=ls.getMax()){
                            ls.setProgress(num);
                            handler.sendEmptyMessageDelayed(WHAT, 50);

                        }else {
                            ls.setError();//进度失败 发生错误

findViewById(R.id.guanxitishi).setVisibility(View.GONE);

TextView tefaf=findViewById(R.id.te3);
tefaf.setVisibility(View.VISIBLE);

                            TextView qinmidu=findViewById(R.id.qinmidu);
                            qinmidu.setVisibility(View.VISIBLE);

                            qinmidu.setText(""+num);
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimacy);

        ls = (SpecialProgressBarView) findViewById(R.id.ls);
        ls.setEndSuccessBackgroundColor(Color.parseColor("#66A269"))//设置进度完成时背景颜色
                .setEndSuccessDrawable(R.drawable.ic_done_white_36dp,null)//设置进度完成时背景图片
                .setCanEndSuccessClickable(false)//设置进度完成后是否可以再次点击开始
                .setProgressBarColor(Color.WHITE)//进度条颜色
                .setCanDragChangeProgress(false)//是否进度条是否可以拖拽
                .setCanReBack(true)//是否在进度成功后返回初始状态
                .setProgressBarBgColor(Color.parseColor("#491C14"))//进度条背景颜色
                .setProgressBarHeight(ls.dip2px(this,4))//进度条宽度
                .setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()))//设置字体大小
                .setStartDrawable(R.drawable.ic_file_upload_white_36dp,null)//设置开始时背景图片
                .setTextColorSuccess(Color.parseColor("#66A269"))//设置成功时字体颜色
                .setTextColorNormal(Color.parseColor("#491C14"))//设置默认字体颜色
                .setTextColorError(Color.parseColor("#BC5246"));//设置错误时字体颜色

        num = 0;
        ls.beginStarting();//启动开始开始动画



        findViewById(R.id.qinmidu).setVisibility(View.GONE);
        findViewById(R.id.te3).setVisibility(View.GONE);

/*       findViewById(R.id.btn_begin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 0;
                ls.beginStarting();//启动开始开始动画
            }
        });*/

/*        findViewById(R.id.btn_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ls.setError();//进度失败 发生错误

                TextView textView=findViewById(R.id.guanxitishi);
                textView.setText("你们的亲密度为"+num);


            }
        });*/

        ls.setOnAnimationEndListener(new SpecialProgressBarView.AnimationEndListener() {

            @Override
            public void onAnimationEnd() {

                ls.setMax(187);
                //ls.setProgress(num);//在动画结束时设置进度
                handler.sendEmptyMessage(WHAT);
            }
        });


        ls.setOntextChangeListener(new SpecialProgressBarView.OntextChangeListener() {
            @Override
            public String onProgressTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return progress * 100 / max + "%";
            }

            @Override
            public String onErrorTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return "error";
            }

            @Override
            public String onSuccessTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return "done";
            }
        });
    }
}
