package com.visitor.tengli.face;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_main)
    Button btnMain;
    @BindView(R.id.btn_cpu)
    Button btnCpu;
    @BindView(R.id.btn_face)
    Button btnFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_main, R.id.btn_cpu, R.id.btn_face})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);
                break;
            case R.id.btn_cpu:
                Intent b = new Intent(this, CPUActivity.class);
                startActivity(b);
                break;
            case R.id.btn_face:
                Intent c = new Intent(this, RtspActivity.class);
                startActivity(c);
                break;
        }
    }
}