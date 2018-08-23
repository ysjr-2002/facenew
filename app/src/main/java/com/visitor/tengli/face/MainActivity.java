package com.visitor.tengli.face;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.hwit.HwitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {


    Unbinder unbinder;

    @BindView(R.id.btnGateOpen)
    Button btnGateOpen;
    @BindView(R.id.btnGateClose)
    Button btnGateClose;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.rb_fan_open)
    RadioButton rbFanOpen;
    @BindView(R.id.rb_fan_close)
    RadioButton rbFanClose;
    @BindView(R.id.btn_fan)
    Button btnFan;
    @BindView(R.id.rg_fan)
    RadioGroup rgFan;
    @BindView(R.id.btn_ligth)
    Button btnLigth;
    @BindView(R.id.rb_white)
    RadioButton rbWhite;
    @BindView(R.id.rb_red)
    RadioButton rbRed;
    @BindView(R.id.rb_green)
    RadioButton rbGreen;
    @BindView(R.id.rb_blue)
    RadioButton rbBlue;
    @BindView(R.id.rb_yellow)
    RadioButton rbYellow;
    @BindView(R.id.rb_close)
    RadioButton rbClose;
    @BindView(R.id.rg_ligth_group)
    RadioGroup rgLigthGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {


        rgLigthGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_white) {
                    openlight(0);
                }
                if (checkedId == R.id.rb_red) {
                    openlight(1);
                }
                if (checkedId == R.id.rb_green) {
                    openlight(2);
                }
                if (checkedId == R.id.rb_blue) {
                    openlight(3);
                }
                if (checkedId == R.id.rb_yellow) {
                    openlight(4);
                }
                if (checkedId == R.id.rb_close) {
                    openlight(5);
                }
            }
        });
    }

    @OnClick({R.id.btnGateOpen, R.id.btnGateClose, R.id.btn_ligth, R.id.btn_fan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGateOpen://开闸
                HwitManager.HwitSetIOValue(5, 1);
                break;
            case R.id.btnGateClose://关闸
                HwitManager.HwitSetIOValue(5, 0);
                break;
            case R.id.btn_ligth:
                int pos = spinner.getSelectedItemPosition();
                openlight(pos);
                break;
            case R.id.btn_fan:
                if (rbFanOpen.isChecked()) {
                    //开风扇
                    HwitManager.HwitSetIOValue(4, 1);
                }
                if (rbFanClose.isChecked()) {
                    //关风扇
                    HwitManager.HwitSetIOValue(4, 0);
                }
                break;
        }
    }

    private void openlight(int pos) {

        if (pos == 0) {
            //白
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (pos == 1) {
            //红
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 2) {
            //绿
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 3) {
            //蓝
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 1);
        } else if (pos == 4) {
            //黄
            HwitManager.HwitSetIOValue(8, 1);
            HwitManager.HwitSetIOValue(9, 1);
            HwitManager.HwitSetIOValue(10, 0);
        } else if (pos == 5) {
            //关
            HwitManager.HwitSetIOValue(8, 0);
            HwitManager.HwitSetIOValue(9, 0);
            HwitManager.HwitSetIOValue(10, 0);
        }
    }
}
