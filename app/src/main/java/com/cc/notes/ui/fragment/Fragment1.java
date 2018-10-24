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
import android.widget.Toast;

import com.cc.notes.XunHome.CardConfig;
import com.cc.notes.XunHome.CardItemTouchHelperCallback;
import com.cc.notes.XunHome.CardLayoutManager;
import com.cc.notes.XunHome.OnSwipeListener;
import com.cc.notes.adapter.MyAdapter;
import com.cc.notes.adapter.SerializableMap;
import com.cc.notes.model.UserBean;
import com.cc.notes.ui.activity.HomeSecondActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment1 extends Fragment {

    private List<Integer> list = new ArrayList<>();
    private List<Map<String,Object>> dataList;
    private MyAdapter.OnItemClickListener listener;
    private MyAdapter adapter;
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

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

        adapter = new MyAdapter(list,getActivity(),dataList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), HomeSecondActivity.class);
                final Map<String, Object> maps = dataList.get(list.get(position));
                Log.e("测试：", (String)maps.get("name"));
                intent.putExtra("name",(String)maps.get("name"));
                //intent.putExtra("img",(String)maps.get("img"));
                intent.putExtra("Birthday",(String)maps.get("Birthday"));
                intent.putExtra("Height",(String)maps.get("Height"));
                intent.putExtra("Introduce",(String)maps.get("Introduce"));
                intent.putExtra("Manifesto",(String)maps.get("Manifesto"));
                intent.putExtra("Occupation",(String)maps.get("Occupation"));
                intent.putExtra("Site",(String)maps.get("Site"));

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
//new Thread(new Runnable() {
//    @Override
//    public void run() {
//        OkHttpUtils.get().addParams("phonenumber","电话号码").url("").build().execute(new StringCallback() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        });
//    }
//}).start();


                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio));

                    System.out.println("是否实训怀执行");


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

//        list.add(0);
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        list.add(6);

        dataList = new ArrayList<Map<String, Object>>();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String restult = post("http://" + getString(R.string.netip) + ":8080/XunTa/UserServlet","");
                    Gson gson = new Gson();
                    ArrayList<UserBean> userBeans = gson.fromJson(restult,new TypeToken<ArrayList<UserBean>>() {
                    }.getType());

                    for (int i = 0; i < userBeans.size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("name", userBeans.get(i).getNickname());
                        map.put("img", userBeans.get(i).getPortrait());
                        map.put("Birthday", userBeans.get(i).getBirthday());
                        map.put("Height", userBeans.get(i).getHeight());
                        map.put("Introduce", userBeans.get(i).getIntroduce());
                        map.put("Manifesto", userBeans.get(i).getManifesto());
                        map.put("Occupation", userBeans.get(i).getOccupation());
                        map.put("Site", userBeans.get(i).getSite());
                        map.put("Sex", userBeans.get(i).getSex());
                        list.add(i);
                        dataList.add(map);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * post请求*/
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }



}
