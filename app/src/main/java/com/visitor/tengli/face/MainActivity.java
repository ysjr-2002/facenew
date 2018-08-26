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
import com.visitor.tengli.face.util.Light;
import com.visitor.tengli.face.util.LightColorEnum;

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
                try {
//                    int result = HwitManager.HwitGetIrqIOValue(1);
//                    if (result == 1) {
//                        tvFaceState.setText("有人");
//                    } else
//                        tvFaceState.setText("无人");
                    int x = 10 / 0;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    tvFaceState.setText("状态读取异常");
                }
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
                    // 可以看到，这里将包含有TEMP关键字的sensor付给了变量mTempSensor
                    // 而这个mTempSensor 就是我们需要的温度传感器
                    mTempSensor = s;
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
        Log.d(Config.tag, sb.toString());
        // 如果传感器不为空，那么我们就可添加一个监听，获取传感器的温度情况
        if (mTempSensor != null) {
            sm.registerListener(mSensorEventListener, mTempSensor
                    , SensorManager.SENSOR_DELAY_GAME);
        }

    }

    // 温度传感器的监听器
    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                if (event.sensor.getStringType().toUpperCase().indexOf("TEMP") > 0) {
                    /*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
                    float temperature = event.values[0];
                    Log.e("temperature: ", String.valueOf(temperature));
                    tvCpuState.setText(String.valueOf(temperature));
//                    sm.unregisterListener(mSensorEventListener, mTempSensor);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    static class SensorTypeName {
        private static String[] itsNames;

        static {
            itsNames = new String[20];
            itsNames[0] = "未知";
            itsNames[Sensor.TYPE_ACCELEROMETER] = "加速度";
            itsNames[Sensor.TYPE_MAGNETIC_FIELD] = "磁力";
            itsNames[Sensor.TYPE_ORIENTATION] = "方向";
            itsNames[Sensor.TYPE_GYROSCOPE] = "陀螺仪";
            itsNames[Sensor.TYPE_LIGHT] = "光线感应";
            itsNames[Sensor.TYPE_PRESSURE] = "压力";
            itsNames[Sensor.TYPE_TEMPERATURE] = "温度";
            itsNames[Sensor.TYPE_PROXIMITY] = "接近,距离传感器";
            itsNames[Sensor.TYPE_GRAVITY] = "重力";
            itsNames[Sensor.TYPE_LINEAR_ACCELERATION] = "线性加速度";
            itsNames[Sensor.TYPE_ROTATION_VECTOR] = "旋转矢量";
            itsNames[Sensor.TYPE_RELATIVE_HUMIDITY] = "TYPE_RELATIVE_HUMIDITY";
            itsNames[Sensor.TYPE_AMBIENT_TEMPERATURE] = "TYPE_AMBIENT_TEMPERATURE";
            itsNames[13] = "TYPE_AMBIENT_TEMPERATURE";
            itsNames[14] = "TYPE_MAGNETIC_FIELD_UNCALIBRATED";
            //itsNames[Sensor.TYPE_GAME_ROTATION_VECTOR] = "TYPE_GAME_ROTATION_VECTOR";
        }

        public static String getSensorTypeName(int type) {
            if (type > 0 && type < itsNames.length) {
                return itsNames[type];
            }
            return "未知";
        }
    }
}
