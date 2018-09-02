package com.visitor.tengli.face;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.model.Student;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.IPHelper;

import java.util.logging.Handler;

import javax.inject.Inject;

import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CAMERA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.KOALA_IP;

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
                final String koala = sp.getStringValue(KOALA_IP, "");
                final String camera = sp.getStringValue(CAMERA_IP, "");

                if (TextUtils.isEmpty(koala) || TextUtils.isEmpty(camera)) {
                    goToSettingActivity();
                } else {
                    boolean a = IPHelper.startPing(koala);
//                    boolean b = IPHelper.startPing(camera);
                    boolean b = true;
                    boolean open = (a && b);
                    open = true;
                    if (open) {
                        goToMainActivity();

                    } else {
                        goToSettingActivity();
                    }
                }
//                Intent intent = new Intent(InitActivity.this, StudyActivity.class);
//                startActivity(intent);
//                InitActivity.this.finish();
            }
        }, 1 * 1000);
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
