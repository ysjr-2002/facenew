package com.visitor.tengli.face;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.hwit.HwitManager;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.DateUtil;
import com.visitor.tengli.face.util.Light;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {


    Unbinder unbinder;

    @BindView(R.id.btnGateOpen)
    Button btnGateOpen;
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

    Timer timer;
    TimerTask timerTask;
    @BindView(R.id.rb_gate_open)
    RadioButton rbGateOpen;
    @BindView(R.id.rb_gate_close)
    RadioButton rbGateClose;
    @BindView(R.id.rg_gate)
    RadioGroup rgGate;
    @BindView(R.id.btn_msg)
    Button btnMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        init();
        initTimerTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initTimerTask() {

        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.d("ysj", DateUtil.getCurrentDateTime());
//                int result = HwitManager.HwitGetIrqIOValue(1);
//                if (result == 1) {
//                    //有人
//                    //开灯
//
//                    closeLigthHandler.removeMessages(0);
//                }
//                else
//                {
//                    //无人
//                }
//                closeLigthHandler.removeMessages(0);
            }
        };

        timer = new Timer();
        Log.d(Config.tag, DateUtil.getCurrentDateTime());
        timer.schedule(timerTask, 5000, 1000);
    }

    //关灯
    Handler closeLigthHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 0) {

                Log.d(Config.tag, "execute");
            }
            return false;
        }
    });


    private void init() {

        rgLigthGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_white) {
                    Light.openlight(0);
                }
                if (checkedId == R.id.rb_red) {
                    Light.openlight(1);
                }
                if (checkedId == R.id.rb_green) {
                    Light.openlight(2);
                }
                if (checkedId == R.id.rb_blue) {
                    Light.openlight(3);
                }
                if (checkedId == R.id.rb_yellow) {
                    Light.openlight(4);
                }
                if (checkedId == R.id.rb_close) {
                    Light.openlight(5);
                }
            }
        });
    }

    @OnClick({R.id.btnGateOpen, R.id.btn_ligth, R.id.btn_fan, R.id.btn_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGateOpen:
                if (rbGateOpen.isChecked()) {
                    HwitManager.HwitSetIOValue(5, 1);
                }
                if (rbGateClose.isChecked()) {
                    HwitManager.HwitSetIOValue(5, 0);
                }
                break;
            case R.id.btn_ligth:
                int pos = spinner.getSelectedItemPosition();
                Light.openlight(pos);
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
            case  R.id.btn_msg:

                closeLigthHandler.sendEmptyMessageDelayed(0, 5000);
                break;
        }
    }
}
