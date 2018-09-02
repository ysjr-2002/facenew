package com.visitor.tengli.face.util;

import android.content.Context;
import android.widget.Toast;

import com.visitor.tengli.face.core.FaceApplication;

/**
 * Created by Shaojie on 2018/5/18.
 */

public class ToastUtil {

    public static void show(String message) {

        Toast.makeText(FaceApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
