package com.visitor.tengli.face.util;

import com.hwit.HwitManager;

public class Light {

    public static void openlight(LightColorEnum color) {

        if (color ==  LightColorEnum.White) {
            //白
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (color ==  LightColorEnum.Red) {
            //红
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (color ==  LightColorEnum.Green) {
            //绿
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (color ==  LightColorEnum.Blue) {
            //蓝
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (color ==  LightColorEnum.Yellow) {
            //黄
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (color ==  LightColorEnum.Close) {
            //关
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        }
    }
}
