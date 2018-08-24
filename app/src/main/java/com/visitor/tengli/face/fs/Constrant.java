package com.visitor.tengli.face.fs;


import com.visitor.tengli.face.util.Util;

/**
 * Created by ysj on 2018/2/27.
 */

public class Constrant {

//    public static String RTSP_CAMERA = "rtsp://%s:8080/h264_ulaw.sdp";

    public  static  String RTSP_CAMERA = "http://%s:8080/video";
    public static String key_welcome = "welcome";
    public static String key_main_server = "server";
    public static String key_slave_server = "slaveserver";
    public static String key_camera = "camera";

    public static String getUrl(String koala, String camera) {
        String url = String.format("ws://%s:9000/video", koala);
        String rtsp = String.format(Constrant.RTSP_CAMERA, camera);
        String rtspUrlEncode = Util.toURLEncoded(rtsp);
        String result = url + "?url=" + rtspUrlEncode;
        return result;
    }
}
