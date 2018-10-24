package com.cc.notes.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.notes.PersonalCenter.CircularImageView;
import com.cc.notes.model.UserInfo;
import com.google.gson.Gson;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

public class PersonalDetails extends AppCompatActivity {

    CircularImageView touxiang;
    ImageView diyizhang, dierzhang, disanzhang;
    TextView zhanghao, xingbie, dizhi, zhiye, xuanyan, nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        new GetData().run();
    }


    private void chushi() {

        touxiang = findViewById(R.id.touxiang2);

        diyizhang = findViewById(R.id.diyizhang);
        dierzhang = findViewById(R.id.dierzhang);
        disanzhang = findViewById(R.id.disanzhang);

        zhanghao = findViewById(R.id.zhanghao);
        xingbie = findViewById(R.id.xingbie);
        dizhi = findViewById(R.id.dizhi);
        zhiye = findViewById(R.id.zhiye);
        xuanyan = findViewById(R.id.xuanyan);
        nicheng = findViewById(R.id.nicheng);
    }

    class GetData implements Runnable {

        @Override
        public void run() {

            OkHttpUtils
                    .get()
                    .url("http://" + getResources().getString(R.string.netip) + ":8080/Findshe/GetFriendsServlet")
                    .build().execute(new ListUserCallback() {
                @Override
                public void onError(Request request, Exception e) {

                }
                @Override
                public void onResponse(List<UserInfo> response) {

                    System.out.println(response.get(0).getNickname());
                }
            });

        }
    }

    public abstract class ListUserCallback extends Callback<List<UserInfo>> {

        @Override
        public List<UserInfo> parseNetworkResponse(Response response) throws IOException {
            String string = response.body().string();
            List<UserInfo> user = new Gson().fromJson(string, List.class);
            return user;
        }
    }


}