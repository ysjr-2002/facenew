package com.visitor.tengli.face.fs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.visitor.tengli.face.entity.FaceRecognized;
import com.visitor.tengli.face.entity.RecognizeState;
import com.visitor.tengli.face.util.Config;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


/**
 * Created by ysj on 2018/2/27.
 */

public class WebSocketHelper {

    private Activity activity;
    private String koala;
    private String camera;
    private WebSocketClient client;
    private boolean mIsHandClose = false;
    private boolean mIsWork = false;
    private Handler handler;

    public WebSocketHelper(Context context, String koala, String camera, Handler handler) {
        this.activity = (Activity) context;
        this.koala = koala;
        this.camera = camera;
        this.handler = handler;
        mIsHandClose = false;
        mIsWork = true;
    }

    private void init() {
        String url = Constrant.getUrl(koala, camera);
        try {
            java.net.URI uri = java.net.URI.create(url);
//            java.net.URI uri = java.net.URI.create("ws://192.168.3.54:4649/Echo");
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    String status = "open";
                    handler.sendEmptyMessage(101);
                    Log.d("ysj", "open");
                }

                @Override
                public void onMessage(String json) {
                    if (mIsWork == false) {
                        return;
                    }
                    if (TextUtils.isEmpty(json)) {
                        return;
                    }

                    Gson gson = new Gson();
                    try {
                        final FaceRecognized face = gson.fromJson(json, FaceRecognized.class);

                        String type = face.type;
                        if (TextUtils.equals(type, RecognizeState.recognized.toString())) {
                            String temp = face.person.avatar;
                            String avatar = "";
                            if (temp.startsWith("http"))
                                avatar = temp;
                            else
                                avatar = "http://" + koala + temp;
                            Log.d("ysj", "message is coming");
                            Bundle bundle = new Bundle();
                            bundle.putString("avatar", avatar);
                            bundle.putString("name", face.person.name);
                            bundle.putString("type", "0");
                            Message message = new Message();
                            message.what = 100;
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                        if (TextUtils.equals(type, RecognizeState.unrecognized.toString())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("avatar", face.data.face.image);
                            bundle.putString("type", "1");
                            Message message = new Message();
                            message.what = 100;
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    if (mIsHandClose) {
                        return;
                    }
                    Log.d(Config.tag, "close");
                    handler.sendEmptyMessage(102);
                    dispose();
                }

                @Override
                public void onError(Exception e) {
//                    error();
                    handler.sendEmptyMessage(103);
                    Log.d(Config.tag, "error");
                }
            };
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void error() {
        client = null;
        reOpen();
    }

    private void dispose() {

        client.close();
        client = null;
        reOpen();
    }

    public void HandClose() {
        mIsHandClose = true;
        client.close();
        client = null;
    }

    public void pause() {
        mIsWork = false;
    }

    public void work() {
        mIsWork = true;
    }

    Thread thread;

    private void reOpen() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    open();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public boolean open() {

        try {
            init();
            client.connect();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
