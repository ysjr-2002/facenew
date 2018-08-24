package com.visitor.tengli.face.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.visitor.tengli.face.MainActivity;

/**
 * created by yangshaojie  on 2018/8/24
 * email: ysjr-2002@163.com
 */
public class BootCompeletReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {

            Intent start = new Intent(context, MainActivity.class);
            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(start);
        }
    }
}
