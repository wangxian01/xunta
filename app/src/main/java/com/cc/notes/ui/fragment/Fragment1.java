package com.cc.notes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cc.notes.XunHome.CardConfig;
import com.cc.notes.XunHome.CardItemTouchHelperCallback;
import com.cc.notes.XunHome.CardLayoutManager;
import com.cc.notes.XunHome.OnSwipeListener;
import com.cc.notes.adapter.MyAdapter;
import com.cc.notes.ui.activity.HomeSecondActivity;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {

    private List<Integer> list = new ArrayList<>();
//    private List<String> list = new ArrayList<>();
    private MyAdapter.OnItemClickListener listener;
    private MyAdapter adapter;




    public Fragment1() {
    }


    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_xun_home, null);
        initDatas();
        //initDataList();
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());






        adapter = new MyAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), HomeSecondActivity.class);
                intent.putExtra("id",list.get(position));
                //intent.putExtras(intent);
                startActivity(intent);
            }
        });
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
new Thread(new Runnable() {
    @Override
    public void run() {
        OkHttpUtils.get().addParams("phonenumber","电话号码").url("").build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}).start();


                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio));




                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                Toast.makeText(getActivity(), direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(getActivity(), "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initDatas();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
//
    private void initDatas() {
//        list.add(R.drawable.touxiang);
//        list.add(R.drawable.img_avatar_02);
//        list.add(R.drawable.img_avatar_03);
//        list.add(R.drawable.img_avatar_04);
//        list.add(R.drawable.img_avatar_05);
//        list.add(R.drawable.img_avatar_06);
//        list.add(R.drawable.img_avatar_07);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);


    }

    /**
     * 初始化适配器需要的数据格式
     */


}
