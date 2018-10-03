package com.cc.notes.PersonalCenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.notes.cc.notes.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PersonalHomeActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home);
        mPersonalBackground = (LinearLayout) findViewById(R.id.personal_background);
        mPersonalPortrait = (CircularImageView) findViewById(R.id.personal_portrait);
        mPersonalName = (TextView) findViewById(R.id.personal_name);
        mPersonalManifesto = (TextView) findViewById(R.id.personal_manifesto);
        mPersonalSex = (TextView) findViewById(R.id.personal_sex);
        mPersonalHeight = (TextView) findViewById(R.id.personal_height);
        mPersonalBirthday = (TextView) findViewById(R.id.personal_birthday);
        mPersonalIntroduce = (TextView) findViewById(R.id.personal_introduce);
        mPersonalMysc = (RelativeLayout) findViewById(R.id.personal_mysc);
        mPersonalScimg = (ImageView) findViewById(R.id.personal_scimg);
        mPersonalMyscnext = (ImageView) findViewById(R.id.personal_myscnext);
        mPersonalMybj = (RelativeLayout) findViewById(R.id.personal_mybj);
        mPersonalBjimg = (ImageView) findViewById(R.id.personal_bjimg);
        mPersonalMybjnext = (ImageView) findViewById(R.id.personal_mybjnext);

        mPersonalMybjnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PersonalHomeActivity.this,EditInformationActivity.class);
                startActivity(intent);
            }
        });

        mPersonalPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("测试：", "点击了");
                showTypeDialog();
            }
        });

    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
//            case 3:
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    head = extras.getParcelable("data");
//                    if (head != null) {
//                        /**
//                         * 上传服务器代码
//                         */
//                        setPicToView(head);// 保存在SD卡中
//                        touxiang.setImageBitmap(head);// 用ImageButton显示出来
//                    }
//                }
//                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

//    private void setPicToView(Bitmap mBitmap) {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//            return;
//        }
//        FileOutputStream b = null;
//        File file = new File(path);
//        file.mkdirs();// 创建文件夹
//        String fileName = path + "head.jpg";// 图片名字
//        try {
//            b = new FileOutputStream(fileName);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
