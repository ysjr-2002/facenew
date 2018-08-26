package com.visitor.tengli.face;

import android.content.Intent;
import android.text.TextUtils;

import com.visitor.tengli.face.fs.WSHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;

public class InitActivity extends BaseActivity {

    SharedPreferencesHelper sp;

    @Override
    int getLayout() {
        return R.layout.activity_init;
    }

    @Override
    void create() {
        sp = SharedPreferencesHelper.getInstance(this);

        final String koala = sp.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
        final String camera = sp.getStringValue(SharedPreferencesHelper.CAMERA_IP, "");

        if (TextUtils.isEmpty(koala) || TextUtils.isEmpty(camera)) {
            goToSettingActivity();
        } else {
            WSHelper ws = new WSHelper(koala, camera);
            boolean open = ws.Open();
            ws.Close();
            if (open) {
                goToMainActivity();

            } else {
                goToSettingActivity();
            }
        }
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
