package com.cc.notes.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.notes.model.Homeinfo;
import com.cc.notes.ui.activity.HomeSecondActivity;
import com.notes.cc.notes.R;

import java.util.List;
import java.util.Map;
//
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Integer> list;
    private Activity activity;
    private Map<String, Object> map;

    public MyAdapter(List<Integer> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
        TextView avatarTextView1 = ((MyViewHolder) holder).tv_age;
        TextView avatarTextView2 = ((MyViewHolder) holder).tv_hangye;
        TextView avatarTextView3 = ((MyViewHolder) holder).tv_xingzuo;
        System.out.println(position);

        // 获取数据显示在各组件
        avatarImageView.setImageResource(Homeinfo.img[list.get(position)]);
        // Log.e("测试", "数据"+(String) map.get("name"));

        ((MyViewHolder) holder).tv_name.setText(Homeinfo.name[list.get(position)]);
        avatarTextView1.setText( Homeinfo.age[list.get(position)]);
        avatarTextView2.setText(Homeinfo.hangye[list.get(position)]);
        avatarTextView3.setText(Homeinfo.xingzuo[list.get(position)]);

//        avatarImageView.setImageResource(list.get(position));
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public  ImageView avatarImageView;
        public  ImageView likeImageView;
        public ImageView dislikeImageView;
        public TextView tv_name;
        public TextView tv_age;
        public TextView tv_xingzuo;
        public TextView tv_hangye;


        public  MyViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_age=(TextView)itemView.findViewById(R.id.tv_age);
            tv_xingzuo=(TextView)itemView.findViewById(R.id.tv_constellation);
            tv_hangye=(TextView)itemView.findViewById(R.id.tv_hangye);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}

