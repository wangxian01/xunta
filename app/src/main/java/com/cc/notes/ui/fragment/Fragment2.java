package com.cc.notes.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cc.notes.adapter.MyRecyclerViewAdapter;
import com.cc.notes.model.UserInfo;
import com.cc.notes.ui.activity.ChatActivity;
import com.cc.notes.ui.activity.IntimacyActivity;
import com.cc.notes.ui.activity.PersonalDetails;
import com.cc.notes.utils.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


/*

created by
tanlin

2018.10.9
*/

public class Fragment2 extends Fragment {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;

    public Fragment2() {

    }

    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainview = null;

        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("getuser", Context.MODE_PRIVATE);
        Log.e(TAG, "onCreateView: "+ sharedPreferences.getBoolean("islogin",false));
        if (sharedPreferences.getBoolean("islogin",false)) {
            mainview = inflater.inflate(R.layout.activity_main_chat, container, false);

            mRecyclerView = mainview.findViewById(R.id.recyclerView);
            //设置RecyclerView管理器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            //设置添加或删除item时的动画，这里使用默认动画
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpUtils
                            .get()
                            .url("http://120.79.180.18/GetFriendsServlet")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Request request, Exception e) {
                                    Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    final ArrayList<UserInfo> homelist;
                                    Gson gson = new Gson();
                                    homelist = gson.fromJson(response, new TypeToken<List<UserInfo>>() {
                                    }.getType());
                                    mAdapter = new MyRecyclerViewAdapter(getActivity(), homelist);
                                    mRecyclerView.setAdapter(mAdapter);

                                    mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            //Toast.makeText(MainActivity.this, "您点击了：  " , Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.setClass(getActivity(), ChatActivity.class);
                                            intent.putExtra("nickname", homelist.get(position).getNickname());
                                            startActivity(intent);
                                        }
                                    });
                                    mAdapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.OnItemLongClickListener() {
                                        @Override
                                        public void onItemLongClick(View view, int position) {
                                            final String[] items3 = new String[]{"查看详细信息", "查看亲密度", "不再关注", "举报"};//创建item
                                            AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                                                    .setTitle("你要对此进行")
                                                    .setIcon(R.mipmap.ic_launcher)
                                                    .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //Toast.makeText(getActivity(), "点的是：" + items3[i], Toast.LENGTH_SHORT).show();
                                                            if (items3[i].equals("查看详细信息")) {
                                                                Intent intent = new Intent();
                                                                intent.setClass(getActivity(), PersonalDetails.class);
                                                                startActivity(intent);
                                                                // Toast.makeText(getActivity(), "这里跳转到查看详细信息界面", Toast.LENGTH_SHORT).show();
                                                            } else if (items3[i].equals("查看亲密度")) {
                                                                //Toast.makeText(getActivity(), "这里跳转到查看亲密度界面", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent();
                                                                intent.setClass(getActivity(), IntimacyActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    })
                                                    .create();
                                            alertDialog3.show();

                                            //Toast.makeText(getActivity(), "您长按点击了：  ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                }
            });
            thread.start();

        } else {
            mainview = inflater.inflate(R.layout.nobody_chatlayout, container, false);
        }
        return mainview;
    }

    //删除关注的方法
    private void delatract(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

}
