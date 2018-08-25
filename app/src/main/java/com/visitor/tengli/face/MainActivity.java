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
import android.widget.TextView;

import com.hwit.HwitManager;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.DateUtil;
import com.visitor.tengli.face.util.Light;
import com.visitor.tengli.face.util.LightColorEnum;

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
    @BindView(R.id.rb_fan_open)
    RadioButton rbFanOpen;
    @BindView(R.id.rb_fan_close)
    RadioButton rbFanClose;
    @BindView(R.id.btn_fan)
    Button btnFan;
    @BindView(R.id.rg_fan)
    RadioGroup rgFan;
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
    @BindView(R.id.tv_face_state)
    TextView tvFaceState;


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
                    Light.openlight(LightColorEnum.White);
                }
                if (checkedId == R.id.rb_red) {
                    Light.openlight(LightColorEnum.Red);
                }
                if (checkedId == R.id.rb_green) {
                    Light.openlight(LightColorEnum.Green);
                }
                if (checkedId == R.id.rb_blue) {
                    Light.openlight(LightColorEnum.Blue);
                }
                if (checkedId == R.id.rb_yellow) {
                    Light.openlight(LightColorEnum.Yellow);
                }
                if (checkedId == R.id.rb_close) {
                    Light.openlight(LightColorEnum.Close);
                }
            }
        });
    }

    @OnClick({R.id.btnGateOpen, R.id.btn_fan, R.id.btn_msg})
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
            case R.id.btn_msg:
                try {
//                    int result = HwitManager.HwitGetIrqIOValue(1);
//                    if (result == 1) {
//                        tvFaceState.setText("有人");
//                    } else
//                        tvFaceState.setText("无人");
                    int x = 10/ 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    tvFaceState.setText("状态读取异常");
                }
                break;
        }
    }
}
