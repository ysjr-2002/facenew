package com.visitor.tengli.face;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.fs.WSHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.model.Student;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.IPHelper;

import java.util.logging.Handler;

import javax.inject.Inject;

public class InitActivity extends BaseActivity {

    @Inject
    SharedPreferencesHelper sp;

    @Override
    int getLayout() {
        return R.layout.activity_init;
    }

    @Override
    void create() {
        Log.d(Config.tag, "Init->" + sp.toString());

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final String koala = sp.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
                final String camera = sp.getStringValue(SharedPreferencesHelper.CAMERA_IP, "");

//                if (TextUtils.isEmpty(koala) || TextUtils.isEmpty(camera)) {
//                    goToSettingActivity();
//                } else {
//                    boolean a = IPHelper.startPing(koala);
//                    boolean b = IPHelper.startPing(camera);
//                    boolean open = (a && b);
//                    if (open) {
//                        goToMainActivity();
//
//                    } else {
//                        goToSettingActivity();
//                    }
//                }

                Intent intent = new Intent(InitActivity.this, HomeActivity.class);
                startActivity(intent);
                InitActivity.this.finish();
            }
        }, 2 * 1000);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    private void goToMainActivity() {
        Intent intent = new Intent(this, RtspActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        this.finish();
    }

    private void goToSettingActivity() {

        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        this.finish();
    }
}
