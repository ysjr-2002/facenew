package com.visitor.tengli.face;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.IPHelper;
import com.visitor.tengli.face.util.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CAMERA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.KOALA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.GREEN_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.RED_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CLOSEGATE_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.STRANGER;

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
    @BindView(R.id.cb_stranger)
    CheckBox cbStranger;

    @Inject
    SharedPreferencesHelper sp;
    @BindView(R.id.et_green)
    EditText etGreen;
    @BindView(R.id.et_red)
    EditText etRed;
    @BindView(R.id.et_close)
    EditText etClose;

    @Override
    int getLayout() {

        return R.layout.activity_setting;
    }

    @Override
    void create() {

        Log.d(Config.tag, "sett->" + sp.toString());

//        sp = SharedPreferencesHelper.getInstance(this);
        String koala = sp.getStringValue(KOALA_IP, "192.168.0.50");
        String camera = sp.getStringValue(CAMERA_IP, "rtsp://192.168.0.15:8080/h264_ulaw.sdp");
        String green = sp.getStringValue(GREEN_DELAY, "2000");
        String red = sp.getStringValue(RED_DELAY, "2000");
        String close = sp.getStringValue(CLOSEGATE_DELAY, "3000");
        boolean stranger = sp.getBooleanValue(STRANGER, false);

        etKoala.setText(koala);
        etCamera.setText(camera);
        etGreen.setText(green);
        etRed.setText(red);
        etClose.setText(close);
        cbStranger.setChecked(stranger);
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }


    @OnClick({R.id.btnExit, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                Process.killProcess(Process.myPid());
                System.exit(0);
                break;
            case R.id.btnSave:
                String koala = etKoala.getText().toString();
                String camera = etCamera.getText().toString();
                String green = etGreen.getText().toString();
                String red = etRed.getText().toString();
                String close = etClose.getText().toString();
                boolean stranger = cbStranger.isChecked();
                sp.setStringValue(KOALA_IP, koala);
                sp.setStringValue(CAMERA_IP, camera);
                sp.setStringValue(GREEN_DELAY, green);
                sp.setStringValue(RED_DELAY, red);
                sp.setStringValue(CLOSEGATE_DELAY, close);
                sp.setBooleanValue(STRANGER, stranger);

                boolean bkoalaping = IPHelper.startPing(koala);
                boolean bcameraping = true; //IPHelper.startPing(camera);
                if (bkoalaping && bcameraping) {
                    Intent intent = new Intent(this, RtspActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                    this.finish();
                } else {
                    ToastUtil.show("连接失败，请检查网络！");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
