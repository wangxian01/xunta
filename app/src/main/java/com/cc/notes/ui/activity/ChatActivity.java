package com.cc.notes.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cc.notes.adapter.MsgAdapter;
import com.cc.notes.model.MsgInfo;
import com.notes.cc.notes.R;

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
    private String path;
    private Socket socket;//客户端套接字
    private MsgAdapter adapter;
    private RecyclerView msgRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // System.out.println("你选择的聊天对象是：    "+getIntent().getStringExtra("nickname"));

        //内置手机存储根目录的路径
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/1.jpg";

        final EditText inputText = (EditText) findViewById(R.id.input);
        Button sendBtn = (Button) findViewById(R.id.send);

        msgRecyclerView = findViewById(R.id.msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        new Thread(netrunnable).start();  //启动子线程

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            String content = inputText.getText().toString();

                            //发送消息
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                            oos.writeObject(new MsgInfo(content, null, "wangxian", "tanlin", "", "没有发送信息", null, null));

                            oos.flush();

                            if ("".equals(content))
                                return;
                            msgList.add(new MsgInfo(content, MsgInfo.TYPE.SENT, null, null, null, null, null, "picture"));

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
    }


    /**
     * 初始化消息数据
     */
/*   private void init() {
        msgList.add(new Msg("你好", Msg.TYPE.RECEIVED,"",""));
        msgList.add(new Msg("你好，请问你是？", Msg.TYPE.SENT));
        msgList.add(new Msg("我是 deniro，很高兴认识你^_^", Msg.TYPE.RECEIVED));
    }*/


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


    //handler 处理返回的请求结果
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
                msgList.add(new MsgInfo(msgg.getContent(), MsgInfo.TYPE.RECEIVED, null, null, null, null, null, null));
                //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                int newSize = msgList.size() - 1;
                adapter.notifyItemInserted(newSize);
                msgRecyclerView.scrollToPosition(newSize);
                //Log.i(msg.getMe() + "发来的信息", msg.getContent());
            }
        }
    };


    //新线程进行网络请求
    Runnable netrunnable = new Runnable() {
        @Override
        public void run() {
            //
            // TODO: http request.
            //

            try {
                socket = new Socket("192.168.1.187", 8888);

                //启动循环监听从服务器发来的消息
                new ClientThread().start();

                //向服务器发送上线信息
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(new MsgInfo(null, null, "wangxian", null, "online", null, null, null));

                oos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Message msg = new Message();
            msg.what = 1;
            Bundle data = new Bundle();
            data.putString("value", "用户上线提醒");
            msg.setData(data);
            handler.sendMessage(msg);

        }
    };
}

