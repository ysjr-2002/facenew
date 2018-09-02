package com.visitor.tengli.face;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.visitor.tengli.face.util.Config.PASSWORD_MAX_LENGTH;
import static com.visitor.tengli.face.util.Config.SETTING_PASSWORD;

public class StudyActivity extends BaseActivity {

    @BindView(R.id.et_pwd)
    TextView mEtPwd;

    @Override
    int getLayout() {
        return R.layout.activity_study;
    }

    @Override
    void create() {
        error_count = 0;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    String mPassword = "";
    @OnClick({R.id.layout_key_i, R.id.layout_key_ii, R.id.layout_key_iii, R.id.layout_key_iv, R.id.layout_key_v, R.id.layout_key_vi, R.id.layout_key_vii, R.id.layout_key_viii, R.id.layout_key_ix, R.id.layout_key_x})
    public void onNumberClicked(View view) {


        mPassword += ((TextView) ((LinearLayout) view).getChildAt(0)).getText();
        mEtPwd.setText(String.format(getString(R.string.key_password_format), mEtPwd.getText()));
    }

    int error_count = 0;

    @OnClick({R.id.layout_key_confirm, R.id.layout_key_delete})
    public void onActionClicked(View view) {

        switch (view.getId()) {
            case R.id.layout_key_delete:
                if (TextUtils.isEmpty(mPassword)) {
                    return;
                }

                mPassword = mPassword.substring(0, mPassword.length() - 1);
                mEtPwd.setText(mEtPwd.getText().toString().substring(0, mPassword.length()));
                break;
            case R.id.layout_key_confirm:

                if (mPassword.length() != PASSWORD_MAX_LENGTH) {
                    ToastUtil.show(String.format(getString(R.string.key_password_length_too_short), PASSWORD_MAX_LENGTH));
                    return;
                }

                if (mPassword.equals(SETTING_PASSWORD)) {
                    mPassword = null;
                    mEtPwd.setText("");
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                    this.finish();
                } else {
                    ToastUtil.show("密码错误！");
                    error_count++;

                    if (error_count == 4) { //错误次数超过三次
                        Intent intent = new Intent(this, RtspActivity.class);
                        startActivity(intent);
                        this.finish();
                    }
                }
        }
    }
}

