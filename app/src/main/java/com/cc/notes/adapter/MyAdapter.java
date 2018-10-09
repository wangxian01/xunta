package com.cc.notes.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.notes.cc.notes.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {

    private List<Integer> list;
    private Activity activity;

    public MyAdapter(List<Integer> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
        avatarImageView.setImageResource(list.get(position));
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


        public  MyViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
            dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);

        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}

