package com.visitor.tengli.face;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.visitor.tengli.face.fs.WSHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.et_koala)
    EditText etKoala;
    @BindView(R.id.et_camera)
    EditText etCamera;
    @BindView(R.id.btnExit)
    Button btnExit;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.root)
    LinearLayout root;

    SharedPreferencesHelper sp;

    @Override
    int getLayout() {

        return R.layout.activity_setting;
    }

    @Override
    void create() {

        sp = SharedPreferencesHelper.getInstance(this);
        String koala = sp.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
        String camera = sp.getStringValue(SharedPreferencesHelper.CAMERA_IP, "");

        if (TextUtils.isEmpty(koala) && TextUtils.isEmpty(camera)) {
            koala = "192.168.0.50";
            camera = "192.168.0.10";
        }
        etKoala.setText(koala);
        etCamera.setText(camera);
    }


    @OnClick({R.id.btnExit, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
            case R.id.btnSave:
                String koala = etKoala.getText().toString();
                String camera = etCamera.getText().toString();
                sp.setStringValue(SharedPreferencesHelper.KOALA_IP, koala);
                sp.setStringValue(SharedPreferencesHelper.CAMERA_IP, camera);
                WSHelper ws = new WSHelper(koala, camera);
                boolean open = ws.Open();
                ws.Close();
                if (open) {
                    Intent intent = new Intent(this, RtspActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                    this.finish();
                } else {
                    ToastUtil.Show(this, "连接失败，请检查网络！");
                }
                break;
        }
    }
}
