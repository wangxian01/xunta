package com.cc.notes.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
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
import com.cc.notes.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notes.cc.notes.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.cc.notes.ui.activity.LoginActivity.JSON;

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
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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

        mPersonalBackground = (LinearLayout)view. findViewById(R.id.personal_background);
        mPersonalName = (TextView) view.findViewById(R.id.personal_name);
        mPersonalManifesto = (TextView)view. findViewById(R.id.personal_manifesto);
        mPersonalSex = (TextView)view. findViewById(R.id.personal_sex);
        mPersonalHeight = (TextView) view.findViewById(R.id.personal_height);
        mPersonalBirthday = (TextView)view. findViewById(R.id.personal_birthday);
        mPersonalIntroduce = (TextView) view.findViewById(R.id.personal_introduce);
        mPersonalMysc = (RelativeLayout) view.findViewById(R.id.personal_mysc);
        mPersonalScimg = (ImageView)view. findViewById(R.id.personal_scimg);
        mPersonalMyscnext = (ImageView) view.findViewById(R.id.personal_myscnext);
        mPersonalMybj = (RelativeLayout) view.findViewById(R.id.personal_mybj);
        mPersonalBjimg = (ImageView) view.findViewById(R.id.personal_bjimg);

//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String restult = post("http://"+getString(R.string.netip)+":8080/XunTa/PersonalCenterServlet","");
//                    Gson gson = new Gson();
//                    ArrayList<UserBean> userBeans = gson.fromJson(restult,new TypeToken<ArrayList<UserBean>>() {
//                    }.getType());
//                    Bitmap bitmap = stringToBitmap(userBeans.get(0).getPortrait());
//                    mPersonalPortrait.setImageBitmap(bitmap);
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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .get()
                        .url("http://" + getString(R.string.netip) + ":8080/XunTa/PersonalCenterServlet")
                        .addParams("userid", "10086") //模拟个人中心id为10086
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                new AlertDialog.Builder(getActivity()).setMessage("网络错误！！").create().show();
                            }
                            @Override
                            public void onResponse(String response) {
                                if (response !=null) {
                                    ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
                                    Gson gson = new Gson();
                                    userBeans = gson.fromJson(response.toString(), new TypeToken<ArrayList<UserBean>>() {
                                    }.getType());
                                    Bitmap bitmap = stringToBitmap(userBeans.get(0).getPortrait());
                                   // Log.e("测试：", String.valueOf(bitmap));
                                    //Log.e("测试：", userBeans.get(0).getPortrait());
                                    mPersonalPortrait.setImageBitmap(bitmap);
                                    mPersonalSex.setText(userBeans.get(0).getSex());
                                    mPersonalHeight.setText(userBeans.get(0).getHeight());
                                    mPersonalBirthday.setText(userBeans.get(0).getBirthday());
                                    mPersonalIntroduce.setText(userBeans.get(0).getIntroduce());
                                } else {

                                }
                            }
                        });
            }
        });
        thread.start();

        /**
         * 点击编辑个人信息
         * */
        mPersonalMybjnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),EditInformationActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 点击更换头像
         * */
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

                /*动态获取权限*/
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }else{
                        startActivityForResult(intent, REQ);  //启动系统相机
                    }
                } else {
                    startActivityForResult(intent, REQ);  //启动系统相机
                }
                //startActivityForResult(intent, REQ);  //启动系统相机
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
                final String bitStrig = bitmapToString(bitmap);
                        Thread threads = new Thread(new Runnable() {
                            @Override
                            public void run() {
                            try {
                                OkHttpUtils
                                .get()
                                .url("http://"+getString(R.string.netip)+":8080/XunTa/ChangePortrait")
                                .addParams("userid", "10086")//模拟修改头像编号10086
                                .addParams("Portrait", bitStrig)
                                .build().execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                                } }
                        });
                    threads.start();
                //Log.e("测试：", String.valueOf(bitStrig));
                //Bitmap bitmaps = stringToBitmap(bitStrig);
                mPersonalPortrait.setImageBitmap(bitmap);    //显示照片
                dialog.dismiss();
            }else if(requestCode == REQ_2){
                Uri imageUri = data.getData();
                Bitmap photoBmp = null;
                if (imageUri != null) {
                    try {
                        photoBmp = getBitmapFormUri(getActivity(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                final String bitStrig = bitmapToString(photoBmp);
                Thread threads = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpUtils
                                    .get()
                                    .url("http://"+getString(R.string.netip)+":8080/XunTa/ChangePortrait")
                                    .addParams("userid", "10086")//模拟修改头像编号10086
                                    .addParams("Portrait", bitStrig)
                                    .build().execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } }
                });
                threads.start();
                //Log.e("测试：", bitStrig);
                mPersonalPortrait.setImageURI(imageUri);
                dialog.dismiss();
            }
        }
    }

    /*
    *将bitmap转换成string
    */
    public String bitmapToString(Bitmap bitmap){
        //将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes, Base64.URL_SAFE);
        return string;
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

    /**
     * 通过uri获取图片并进行压缩 
     *  @param ac
     * @param uri
     */
    public static Bitmap getBitmapFormUri(FragmentActivity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional  
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional  
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩  
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例  
        bitmapOptions.inDither = true;//optional  
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional  
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩  
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
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
