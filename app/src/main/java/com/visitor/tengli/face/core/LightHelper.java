package com.visitor.tengli.face.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hwit.HwitManager;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.Light;
import com.visitor.tengli.face.util.LightColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */

@Singleton
public class LightHelper {

    long currenttime;
    Timer timer;
    TimerTask timerTask;
    LightColor lightColor;
    IFaceListener faceNear;

    int ligth_green_to_white = 2000;
    int ligth_red_to_white = 2000;

    @Inject
    public LightHelper() {
    }

    public void setFaceListerner(IFaceListener faceNear) {
        this.faceNear = faceNear;
    }

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLigth_green_to_white(int val) {
        ligth_green_to_white = val;
    }

    public void setLigth_red_to_white(int val) {
        this.ligth_red_to_white = val;
    }

    public void run() {
        initTimerTask();
    }

    private void initTimerTask() {

        timerTask = new TimerTask() {
            @Override
            public void run() {

                int temperature = 0;
//                try {
//                    temperature = HwitManager.HwitGetCpuTemp();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
                if (temperature >= 50) {
                    HwitManager.HwitSetIOValue(4, 1);
                } else if (temperature > 0) {
                    HwitManager.HwitSetIOValue(4, 0);
                }

                int result = Light.getFace();
                if (result == 1) {
                    SwitchLigth(LightColor.White);
                } else {
                    SwitchLigth(lightColor.Close);
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 5000);
    }

    private String readfile(String path) {


        File file = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            inputStream.close();
            return line;
        } catch (Exception ex) {
            String ok = ex.getMessage();
            ex.printStackTrace();
            return "shit->" + ok;
        }
    }

    public void SwitchLigth(LightColor color) {

        if (this.lightColor == color) {
            currenttime = System.currentTimeMillis();
            return;
        }

        if (color == LightColor.White && this.lightColor == LightColor.Green) {

            long span = System.currentTimeMillis() - currenttime;
            if (span <= ligth_green_to_white) {
                return;
            }
        }

        if (color == LightColor.White && this.lightColor == LightColor.Red) {

            long span = System.currentTimeMillis() - currenttime;
            if (span <= ligth_red_to_white) {
                return;
            }
        }

        if (color == LightColor.White) {
            //触发人脸靠近事件
            faceNear.near();
        }
        if (color == LightColor.Close) {
            faceNear.leave();
        }

        this.lightColor = color;
        Light.openlight(color);
        currenttime = System.currentTimeMillis();
    }

    public void open() {
        HwitManager.HwitSetIOValue(5, 1);
    }

    public void close() {
        HwitManager.HwitSetIOValue(5, 0);
    }

    public void stop() {

        timer.cancel();
    }
}
