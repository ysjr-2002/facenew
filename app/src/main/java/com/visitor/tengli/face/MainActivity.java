package com.visitor.tengli.face;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
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
import com.visitor.tengli.face.util.IPHelper;
import com.visitor.tengli.face.util.Light;
import com.visitor.tengli.face.util.LightColor;
import com.visitor.tengli.face.util.SensorTypeName;

import java.util.List;
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
    @BindView(R.id.tv_cpu_state)
    TextView tvCpuState;
    @BindView(R.id.btn_cpu)
    Button btnCpu;


    SensorManager sm = null;
    List<Sensor> allSensors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        allSensors = sm.getSensorList(Sensor.TYPE_ALL);

        closeLigthHandler.sendEmptyMessageDelayed(101, 3000);

        init();
        initTimerTask();

        getcpu();

        float a = 23.4f;
        int b = (int) a;
        String c = "";

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(mSensorEventListener);
        unbinder.unbind();
    }

    private void initTimerTask() {

        timerTask = new TimerTask() {
            @Override
            public void run() {

//                Log.d("ysj", "当前时间->" + DateUtil.getCurrentDateTime());
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
            if (msg.what == 101) {

            }
            return false;
        }
    });


    private void init() {

        rgLigthGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_white) {
                    Light.openlight(LightColor.White);
                }
                if (checkedId == R.id.rb_red) {
                    Light.openlight(LightColor.Red);
                }
                if (checkedId == R.id.rb_green) {
                    Light.openlight(LightColor.Green);
                }
                if (checkedId == R.id.rb_blue) {
                    Light.openlight(LightColor.Blue);
                }
                if (checkedId == R.id.rb_yellow) {
                    Light.openlight(LightColor.Yellow);
                }
                if (checkedId == R.id.rb_close) {
                    Light.openlight(LightColor.Close);
                }
            }
        });
    }

    @OnClick({R.id.btnGateOpen, R.id.btn_fan, R.id.btn_msg, R.id.btn_cpu})
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
                int result = HwitManager.HwitGetIrqIOValue(1);
                if (result == 1) {
                    tvFaceState.setText("有人");
                } else
                    tvFaceState.setText("无人");
                break;
            case R.id.btn_cpu:
                getcpu();
                break;
        }
    }


    private void getcpu() {
        String typeName = "";
        Sensor mTempSensor = null;
        StringBuilder sb = new StringBuilder();
        for (Sensor s : allSensors) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                typeName = SensorTypeName.getSensorTypeName(s.getType()) + " " + s.getStringType();
                if (s.getStringType().toUpperCase().indexOf("TEMP") > 0) {
                    mTempSensor = s;
                    String temp1 = s.getStringType();
                }
            } else {
                typeName = SensorTypeName.getSensorTypeName(s.getType()) + " " + s.getType();
            }
            sb.append(String.format("\t类型:%s\n", typeName));
            sb.append(String.format("\t设备名称:%s\n", s.getName()));
            sb.append(String.format("\t设备版本:%s\n", s.getVersion()));
            sb.append(String.format("\t供应商:%s\n", s.getVendor()));
            sb.append("\n");
        }
        String x = sb.toString();
        Log.d(Config.tag, sb.toString());
        // 如果传感器不为空，那么我们就可添加一个监听，获取传感器的温度情况


//        mTempSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (mTempSensor != null) {
            sm.registerListener(mSensorEventListener, mTempSensor
                    , SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // 温度传感器的监听器
    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE || event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {      /*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
                float temperature = event.values[0];
                Log.e("temperature: ", String.valueOf(temperature));
                tvCpuState.setText(String.valueOf(temperature));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}