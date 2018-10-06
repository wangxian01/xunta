package com.cc.notes.adapter;

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

        LinearLayout leftLayout;
        LinearLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;

        ImageView zuobian,youbian,falaidetupian,fachudetupian;

        public ViewHolder(View itemView) {
            super(itemView);
            fachudetupian=itemView.findViewById(R.id.fachuqudetupian);
            falaidetupian=itemView.findViewById(R.id.falaidetupian);
            youbian=itemView.findViewById(R.id.youbiantouxiang);
            zuobian=itemView.findViewById(R.id.zuobiantouxiang);
            leftLayout=(LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout=(LinearLayout)itemView.findViewById(R.id.right_layout);
            leftMsg=(TextView) itemView.findViewById(R.id.left_msg);
            rightMsg=(TextView) itemView.findViewById(R.id.right_msg);
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
                }else {
                    holder.leftLayout.setVisibility(View.VISIBLE);
                    holder.rightLayout.setVisibility(View.GONE);
                    holder.youbian.setVisibility(View.GONE);
                    holder.leftMsg.setText(msg.getContent());
                }
                break;
            case SENT://发出的消息
                if(msg.getFiletype().equals("picture")){
                    holder.zuobian.setVisibility(View.GONE);//左边头像不显示
                    holder.leftLayout.setVisibility(View.GONE);//左边布局不显示
                    holder.rightLayout.setVisibility(View.VISIBLE);//右边布局显示
                    holder.rightMsg.setVisibility(View.GONE);//右边文字不显示
                    holder.fachudetupian.setVisibility(View.VISIBLE);

                }else {
                    holder.zuobian.setVisibility(View.GONE);
                    holder.leftLayout.setVisibility(View.GONE);
                    holder.rightLayout.setVisibility(View.VISIBLE);
                    holder.rightMsg.setText(msg.getContent());
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
