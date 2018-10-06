package com.cc.notes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cc.notes.adapter.MyRecyclerViewAdapter;
import com.cc.notes.model.UserInfo;
import com.cc.notes.ui.activity.ChatActivity;
import com.cc.notes.utils.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

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

        View view=inflater.inflate(R.layout.activity_main_chat, container, false);


            mRecyclerView =  view. findViewById(R.id.recyclerView);
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
                                    Toast.makeText(getActivity(), "网络连接异常",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    final ArrayList<UserInfo> homelist;
                                    Gson gson = new Gson();
                                    homelist = gson.fromJson(response, new TypeToken<List<UserInfo>>() {
                                    }.getType());
                                    mAdapter = new MyRecyclerViewAdapter(getActivity(),homelist);
                                    mRecyclerView.setAdapter(mAdapter);

                                    mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            //Toast.makeText(MainActivity.this, "您点击了：  " , Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.setClass(getActivity(), ChatActivity.class);
                                            intent.putExtra("nickname",homelist.get(position).getNickname());
                                            startActivity(intent);
                                        }
                                    });

                                    mAdapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.OnItemLongClickListener() {
                                        @Override
                                        public void onItemLongClick(View view, int position) {
                                            Toast.makeText(getActivity(), "您长按点击了：  ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                }
            });
            thread.start();


return view;


    }

}
