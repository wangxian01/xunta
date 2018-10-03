package com.cc.notes.ui.fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.notes.PersonalCenter.CircularImageView;
import com.cc.notes.PersonalCenter.EditInformationActivity;
import com.cc.notes.PersonalCenter.PersonalHomeActivity;
import com.notes.cc.notes.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class Fragment3 extends Fragment {
    private LinearLayout mPersonalBackground;
    private CircularImageView mPersonalPortrait;
    private TextView mPersonalName;
    private TextView mPersonalManifesto;
    private TextView mPersonalSex;
    private TextView mPersonalHeight;
    private TextView mPersonalBirthday;
    private TextView mPersonalIntroduce;
    private RelativeLayout mPersonalMysc;
    private ImageView mPersonalScimg;
    private ImageView mPersonalMyscnext;
    private RelativeLayout mPersonalMybj;
    private ImageView mPersonalBjimg;
    private ImageView mPersonalMybjnext;
    private static int REQ = 1;
    private static int REQ_2 = 2;
    private AlertDialog dialog;
    public Fragment3() {
    }


    public static Fragment3 newInstance() {
        Fragment3 fragment = new Fragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_home, container, false);

        mPersonalMybjnext = (ImageView)view. findViewById(R.id.personal_mybjnext);
        mPersonalPortrait = (CircularImageView)view.findViewById(R.id.personal_portrait);
        mPersonalMybjnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),EditInformationActivity.class);
                startActivity(intent);
            }
        });

        mPersonalPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

        return view;
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);

        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_2);
            }
        });

        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //调用系统相机
                startActivityForResult(intent, REQ);  //启动系统相机
            }
        });
        dialog.setView(view);
        dialog.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == REQ){
                Bundle bundle = data.getExtras();      //获取拍摄信息
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mPersonalPortrait.setImageBitmap(bitmap);    //显示照片
                dialog.dismiss();
            }else if(requestCode == REQ_2){
                Uri imageUri = data.getData();
                mPersonalPortrait.setImageURI(imageUri);
                dialog.dismiss();
            }
        }
    }


}
