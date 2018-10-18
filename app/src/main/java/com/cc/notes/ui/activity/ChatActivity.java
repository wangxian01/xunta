package com.cc.notes.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.notes.PersonalCenter.CircularImageView;
import com.cc.notes.Service.SocketService;
import com.cc.notes.adapter.MsgAdapter;
import com.cc.notes.model.MsgInfo;
import com.notes.cc.notes.R;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<MsgInfo> msgList = new ArrayList<>();
    //private String path;


    private String fanhuidehaoma;
    private Socket socket;
    private MsgAdapter adapter;
    private RecyclerView msgRecyclerView;
    private static int REQ = 1;
    private static int REQ_2 = 2;
    private AlertDialog dialog;
    public String me;
    SocketService.MyBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (SocketService.MyBinder)service;
            socket=myBinder.getService().socket;

            //启动循环监听从服务器发来的消息
            new ClientThread().start();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //开启服务
        Intent bindIntent = new Intent(this, SocketService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);

        //System.out.println("你选择的聊天对象是：    "+getIntent().getStringExtra("nickname"));
        //内置手机存储根目录的路径
       // path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/1.jpg";

        final EditText inputText = findViewById(R.id.input);
        Button sendBtn = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg);

        TextView nizi=findViewById(R.id.yonghunicheng);
        nizi.setText(getIntent().getStringExtra("nickname"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

//根据你选择的用户昵称查询到该用户的id，传给接收对象
        OkHttpUtils
                .get()
                .url("http://192.168.1.187:8080/nicknameServlet")
                .addParams("guanjianzi",getIntent().getStringExtra("nickname"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        fanhuidehaoma=response;
                    }
                });


      // new Thread(netrunnable).start();  //启动子线程

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                            String content = inputText.getText().toString();
                            //发送消息
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            MsgInfo xinxi=new MsgInfo(content, MsgInfo.TYPE.SENT, sharedPreferences.getString("name","13795971992"), fanhuidehaoma, "", "没有发送信息", null, "msg");
                            oos.writeObject(xinxi);
                            oos.flush();
                            if ("".equals(content))
                                return;
                            msgList.add(xinxi);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                                    int newSize = msgList.size() - 1;
                                    adapter.notifyItemInserted(newSize);
                                    msgRecyclerView.scrollToPosition(newSize);
                                    //清空输入框中的内容
                                    inputText.setText("");
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });


        //图片按钮监听
        findViewById(R.id.sendpic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

    }


    /**
     * 接收从服务器发送的信息
     *
     * @author Jaye Li
     */
    class ClientThread extends Thread {

        public void run() {

            try {

                while (true) {

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    MsgInfo msg = (MsgInfo) ois.readObject();

                    Message message = Message.obtain();
                    message.obj = msg;
                    message.what = 2;
                    handler.sendMessage(message);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    //handler 处理返回的请求结果
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                Bundle data = msg.getData();
                String val = data.getString("value");
                //
                // TODO: 更新界面
                //
                Log.i("mylog", "请求结果-->" + val);
            }
            if (msg.what == 2) {
                MsgInfo msgg = (MsgInfo) msg.obj;
                System.out.println("收到的内容" + msgg.getContent());
                msgList.add(new MsgInfo(msgg.getContent(), MsgInfo.TYPE.RECEIVED, null, null, null, msgg.getSendtime(), msgg.getPicture(), msgg.getFiletype()));
                //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                int newSize = msgList.size() - 1;
                adapter.notifyItemInserted(newSize);
                msgRecyclerView.scrollToPosition(newSize);
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == REQ){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
                else {
                    Bundle bundle = data.getExtras();      //获取拍摄信息
                    final Bitmap bitmap = (Bitmap) bundle.get("data");
                    Log.i("TGAAAAAAA","高度为      "+bitmap.getHeight()+"宽度为    "+bitmap.getWidth()+"大小为  "+bitmap.getByteCount() / 1024 / 1024);
                    // mPersonalPortrait.setImageBitmap(bitmap);    //显示照片
                    //发送拍摄的图片
                   new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);
                                ObjectOutputStream   oos = new ObjectOutputStream(socket.getOutputStream());
                                MsgInfo hehe=new MsgInfo(null, MsgInfo.TYPE.SENT, sharedPreferences.getString("name","13795971992"), fanhuidehaoma, null, null, Bitmap2Bytes(bitmap), "picture");
                                oos.writeObject(hehe);
                                msgList.add(hehe);
                                oos.flush();

                                Message message = Message.obtain();
                                message.obj = hehe;
                                message.what = 3;
                                handler.sendMessage(message);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                          runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                                    int newSize = msgList.size() - 1;
                                    adapter.notifyItemInserted(newSize);
                                    msgRecyclerView.scrollToPosition(newSize);
                                }
                            });

                        }
                    }).start();
                    dialog.dismiss();

                }

                //从相册获取图片发送
            }else if(requestCode == REQ_2){
                //final Uri imageUri = data.getData();
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
                else{

                    final String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SharedPreferences sharedPreferences = getSharedPreferences("getuser", Context.MODE_PRIVATE);

                                MsgInfo hehe=new MsgInfo(null, MsgInfo.TYPE.SENT, sharedPreferences.getString("name","13795971992"), fanhuidehaoma, "", null, File2byte(realPathFromUri), "picture");

                                msgList.add(hehe);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                                        int newSize = msgList.size() - 1;
                                        adapter.notifyItemInserted(newSize);
                                        msgRecyclerView.scrollToPosition(newSize);
                                    }
                                });

                                //把图片发送到服务端
                                ObjectOutputStream  oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.writeObject(hehe);
                                oos.flush();



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                    // mPersonalPortrait.setImageURI(imageUri);
                    dialog.dismiss(); };
            }
        }
    }

    //新线程进行网络请求
    Runnable netrunnable = new Runnable() {

        @Override
        public void run() {

                //socket = new Socket("172.17.162.160", 8888);

                //启动循环监听从服务器发来的消息
              new ClientThread().start();

 /*                 //向服务器发送上线信息
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(new MsgInfo(null, null, "王贤", "谭林", "online", null, null, null));

                oos.flush();*/

            Message msg = new Message();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putString("value", "用户上线提醒");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);

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
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ChatActivity.this,new String[]{Manifest.permission.CAMERA},222);
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

//把Cursor的路径转化为存储路径
    public static class RealPathFromUriUtils {
        /**
         * 根据Uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        public static String getRealPathFromUri(Context context, Uri uri) {
            int sdkVersion = Build.VERSION.SDK_INT;
            if (sdkVersion >= 19) { // api >= 19
                return getRealPathFromUriAboveApi19(context, uri);
            } else { // api < 19
                return getRealPathFromUriBelowAPI19(context, uri);
            }
        }

        /**
         * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
            return getDataColumn(context, uri, null, null);
        }

        /**
         * 适配api19及以上,根据uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        @SuppressLint("NewApi")
        private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
            String filePath = null;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // 如果是document类型的 uri, 则通过document id来进行处理
                String documentId = DocumentsContract.getDocumentId(uri);
                if (isMediaDocument(uri)) { // MediaProvider
                    // 使用':'分割
                    String id = documentId.split(":")[1];

                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = {id};
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                    filePath = getDataColumn(context, contentUri, null, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是 content 类型的 Uri
                filePath = getDataColumn(context, uri, null, null);
            } else if ("file".equals(uri.getScheme())) {
                // 如果是 file 类型的 Uri,直接获取图片对应的路径
                filePath = uri.getPath();
            }
            return filePath;
        }

        /**
         * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
         *
         * @return
         */
        private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            String path = null;

            String[] projection = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                    path = cursor.getString(columnIndex);
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return path;
        }

        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is MediaProvider
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is DownloadsProvider
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);

        Intent stopIntent = new Intent(ChatActivity.this, SocketService.class);
        stopService(stopIntent);

    }
}

