package com.cc.notes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.notes.model.UserInfo;
import com.cc.notes.utils.CircularImageView;
import com.notes.cc.notes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 青青-子衿 on 2018/1/15.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private Context mcontext;
    private ArrayList<UserInfo> friendinfo;


    public MyRecyclerViewAdapter(Context context,ArrayList<UserInfo> friendinfo1) {
        this.friendinfo=friendinfo1;
        this.mcontext=context;
    }

    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按点击事件
     */

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }



    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendslist_item, parent, false);
        MyRecyclerViewAdapter.ViewHolder viewHolder = new MyRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        System.out.println(friendinfo.size());
        holder.mText.setText(friendinfo.get(position).getNickname());
        holder.ttext.setText(friendinfo.get(position).getSignature());
        Picasso.with(mcontext).load("http://120.79.180.18/images/"+friendinfo.get(position).getAvatar_name()+".jpg").into(holder.touxiang);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new MyOnClickListener(position));
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new MyOnLongClickListener(position));
        }
    }


    @Override
    public int getItemCount() {
        return friendinfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText,ttext;
        CircularImageView touxiang;

        ViewHolder(View itemView) {
            super(itemView);
            touxiang=itemView.findViewById(R.id.roundImageView);
            mText = itemView.findViewById(R.id.nicheng);
            ttext=itemView.findViewById(R.id.qianming);
        }
    }



    private class MyOnLongClickListener implements View.OnLongClickListener {
        private int position;

        public MyOnLongClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(v, position);
            return true;
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, position);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
