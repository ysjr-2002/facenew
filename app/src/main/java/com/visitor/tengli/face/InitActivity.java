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

        final String koala = sp.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
        final String camera = sp.getStringValue(SharedPreferencesHelper.CAMERA_IP, "");

        if (TextUtils.isEmpty(koala) || TextUtils.isEmpty(camera)) {
            goToSettingActivity();
        } else {
//            WSHelper ws = new WSHelper(koala, camera);
//            boolean open = ws.Open();
//            ws.Close();
            boolean a = IPHelper.startPing(koala);
            boolean b = IPHelper.startPing(camera);
            boolean open = (a && b);
            if (open) {
                goToMainActivity();

            } else {
                goToSettingActivity();
            }
        }
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
