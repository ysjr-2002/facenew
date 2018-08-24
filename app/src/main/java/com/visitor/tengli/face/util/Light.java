package com.visitor.tengli.face.util;

import com.hwit.HwitManager;

public class Light {

    public static void openlight(int pos) {

        if (pos == 0) {
            //白
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (pos == 1) {
            //红
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 2) {
            //绿
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 3) {
            //蓝
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (pos == 4) {
            //黄
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 5) {
            //关
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        }
    }
}
