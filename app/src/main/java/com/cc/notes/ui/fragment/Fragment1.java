package com.cc.notes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.notes.XunHome.CardConfig;
import com.cc.notes.XunHome.CardItemTouchHelperCallback;
import com.cc.notes.XunHome.CardLayoutManager;
import com.cc.notes.XunHome.OnSwipeListener;
import com.cc.notes.adapter.MyAdapter;
import com.cc.notes.ui.activity.HomeSecondActivity;
import com.notes.cc.notes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment1 extends Fragment {

    private List<Integer> list = new ArrayList<>();
    private MyAdapter.OnItemClickListener listener;
    private MyAdapter adapter;
    private List<Map<String,Object>> dataList;

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
        initDataList();
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyAdapter(list,dataList,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(getActivity(), HomeSecondActivity.class));
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
        list.add(R.drawable.touxiang);
        list.add(R.drawable.img_avatar_02);
        list.add(R.drawable.img_avatar_03);
        list.add(R.drawable.img_avatar_04);
        list.add(R.drawable.img_avatar_05);
        list.add(R.drawable.img_avatar_06);
        list.add(R.drawable.img_avatar_07);
    }

    /**
     * 初始化适配器需要的数据格式
     */

    private void initDataList() {
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <= 6; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
                        map.put("name","名字" +i);
                        map.put("age","年龄" +i);
                        map.put("hangye","行业" +i);
                        map.put("xingzuo","星座" +i);
                        dataList.add(map);
        }
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String restult = post("http://"+getString(R.string.netip)+":8080/AndroidServers/ChoicenesServlet","");
//                    Gson gson = new Gson();
//                    ArrayList<BeanChoiceness> beanChoicenesses = gson.fromJson(restult,new TypeToken<ArrayList<BeanChoiceness>>() {
//                    }.getType());
//
//                    for (int i = 0; i < beanChoicenesses.size(); i++) {
//                        Map<String, Object> map = new HashMap<String, Object>();
//                        map.put("UpId", beanChoicenesses.get(i).getUpId());
//                        map.put("UpPortrait", beanChoicenesses.get(i).getUpPortrait() );
//                        map.put("ChoicenessUpName",beanChoicenesses.get(i).getUpName());
//                        map.put("ChoicenessUpTime", beanChoicenesses.get(i).getUpTime());
//                        map.put("ChoicenessUpText", beanChoicenesses.get(i).getUpText());
//                        map.put("UpIntroduce", beanChoicenesses.get(i).getUpIntroduce());
//                        map.put("VideoImg", beanChoicenesses.get(i).getVideoImg());
//                        map.put("ChoicenessViodeoview", beanChoicenesses.get(i).getVideoAddress());
//                        map.put("UpLike", beanChoicenesses.get(i).getUpLike());
//                        map.put("LikeNumber", beanChoicenesses.get(i).getLikeNumber());
//                        map.put("UpStep",beanChoicenesses.get(i).getUpStep());
//                        map.put("StepNumber", beanChoicenesses.get(i).getStepNumber());
//                        dataList.add(map);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
//        try {
//            thread.join();//使线程 从异步执行 变成同步执行
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}
