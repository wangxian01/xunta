package com.cc.notes.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.notes.model.Homeinfo;
import com.notes.cc.notes.R;

import java.util.List;
import java.util.Map;
//
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Integer> list;
    private Activity activity;
    private Map<String, Object> map;
    private List<Map<String,Object>> dataList;

    public MyAdapter(List<Integer> list, Activity activity, List<Map<String, Object>> dataList) {
        this.list = list;
        this.activity = activity;
        this.dataList = dataList;
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

        // 获取数据显示在各组件
        //avatarImageView.setImageResource(Homeinfo.img[list.get(position)]);
        final Map<String, Object> map = dataList.get(list.get(position));
        // Log.e("测试", "数据"+(String) map.get("name"));
        Bitmap bitmap = stringToBitmap((String)map.get("img"));
        // Log.e("测试：", String.valueOf(bitmap));
        //Log.e("测试：", userBeans.get(0).getPortrait());
        avatarImageView.setImageBitmap(bitmap);
        ((MyViewHolder) holder).tv_name.setText((String)map.get("name"));
//        ((MyViewHolder) holder).tv_name.setText(Homeinfo.name[list.get(position)]);
        avatarTextView1.setText( (String)map.get("Birthday")+" ♀");
        avatarTextView2.setText((String)map.get("Height0")+" cm");
        avatarTextView3.setText((String)map.get("Occupation"));
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

    /**
     * 将字符串转换成Bitmap类型
     * */
    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.URL_SAFE);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}

