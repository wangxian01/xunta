package com.cc.notes.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cc.notes.model.MsgInfo;
import com.notes.cc.notes.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgInfo> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout,rightLayout;
        TextView leftMsg,rightMsg;
        ImageView zuobian,youbian,falaidetupian,fachudetupian;

        public ViewHolder(View itemView) {
            super(itemView);
            fachudetupian=itemView.findViewById(R.id.fachudetupian);
            falaidetupian=itemView.findViewById(R.id.shoudaodetupian);
            youbian=itemView.findViewById(R.id.youbiantouxiang);
            zuobian=itemView.findViewById(R.id.zuobiantouxiang);
            leftLayout= itemView.findViewById(R.id.left_layout);
            rightLayout=itemView.findViewById(R.id.right_layout);
            leftMsg=itemView.findViewById(R.id.left_msg);
            rightMsg= itemView.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<MsgInfo> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgInfo msg=msgList.get(position);
        switch (msg.getType()){
            case RECEIVED://接收的消息
                if(msg.getFiletype().equals("picture")){
                    holder.leftLayout.setVisibility(View.VISIBLE);//左边布局显示
                    holder.leftMsg.setVisibility(View.GONE);//左边文字不显示
                    holder.youbian.setVisibility(View.GONE);//右边头像不显示
                    holder.rightLayout.setVisibility(View.GONE);//右边的布局不显示
                    holder.falaidetupian.setVisibility(View.VISIBLE);//左边显示发来的图片
                    holder.falaidetupian.setImageBitmap(Bytes2Bimap(msg.getPicture()));//左边设置发来的图片
                }else if(msg.getFiletype().equals("msg")) {
                    holder.leftLayout.setVisibility(View.VISIBLE);//左边布局显示
                    holder.leftMsg.setText(msg.getContent());//左边文本框设置文字
                    holder.falaidetupian.setVisibility(View.GONE);//左边图片不显示
                    holder.rightLayout.setVisibility(View.GONE);//右边布局不显示
                    holder.youbian.setVisibility(View.GONE);//右边头像不显示
                }
                break;
            case SENT://发出的消息
                if(msg.getFiletype().equals("picture")){
                    holder.zuobian.setVisibility(View.GONE);//左边头像不显示
                    holder.leftLayout.setVisibility(View.GONE);//左边布局不显示
                    holder.rightLayout.setVisibility(View.VISIBLE);//右边布局显示
                    holder.rightMsg.setVisibility(View.GONE);//右边文字不显示
                    holder.fachudetupian.setVisibility(View.VISIBLE);//右边图片显示
                    holder.fachudetupian.setImageBitmap(Bytes2Bimap(msg.getPicture()));

                }else if(msg.getFiletype().equals("msg")) {
                    holder.zuobian.setVisibility(View.GONE);//左边头像不显示
                    holder.leftLayout.setVisibility(View.GONE);//左边布局不显示
                    holder.rightLayout.setVisibility(View.VISIBLE);//右边布局显示
                    holder.rightMsg.setText(msg.getContent());//右边文本设置文字
                    holder.fachudetupian.setVisibility(View.GONE);//右边图片不显示
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

}
