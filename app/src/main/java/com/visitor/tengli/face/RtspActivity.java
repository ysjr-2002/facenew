package com.visitor.tengli.face;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hwit.HwitManager;
import com.squareup.picasso.Picasso;
import com.visitor.tengli.face.core.IFaceListener;
import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.fs.WebSocketHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.util.IPHelper;
import com.visitor.tengli.face.util.LightColor;
import com.visitor.tengli.face.util.SensorTypeName;
import com.visitor.tengli.face.util.ToastUtil;
import com.visitor.tengli.face.view.DiffuseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RtspActivity extends AppCompatActivity implements IFaceListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.image_face)
    CircleImageView imageFace;
    @BindView(R.id.tvopen)
    TextView tvopen;
    @BindView(R.id.rl_face_root)
    FrameLayout rlFaceRoot;

    String koala;
    String camera;
    SharedPreferencesHelper sp;
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    @BindView(R.id.diffuseView)
    DiffuseView diffuseView;

    WebSocketHelper webSocketHelper;
    LightHelper lightHelper;

    SensorManager sm = null;
    List<Sensor> allSensors = null;

    final int cpu_max_temperature = 50;
    final int cpu_min_temperature = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);
        ButterKnife.bind(this);

        sp = SharedPreferencesHelper.getInstance(this);
        koala = sp.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
        camera = sp.getStringValue(SharedPreferencesHelper.CAMERA_IP, "");
        initView();

        lightHelper = new LightHelper();
        lightHelper.run();

        diffuseView.start();

        getcpu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(mSensorEventListener);
        lightHelper.stop();
        diffuseView.stop();
    }

    private void showface(String avatar) {


        Picasso.with(this).load(avatar).into(imageFace);
        rlFaceRoot.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.big);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                handler.removeMessages(200);
                handler.sendEmptyMessageDelayed(200, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlFaceRoot.startAnimation(animation);
    }

    private void initView() {

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadsImagesAutomatically(true);

        webview.setWebChromeClient(new WebChromeClient() {

        });

//        webview.loadUrl("http://127.0.0.1:8080/browserfs.html");
        webview.loadUrl("http://www.baidu.com");
        webSocketHelper = new WebSocketHelper(this, koala, camera, handler);
        webSocketHelper.open();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if (message.what == 100) {

                Bundle bundle = message.getData();
                String type = bundle.getString("type");
                if (type == "0") {
                    String personname = bundle.getString("name");
                    String avatar = bundle.getString("avatar");
                    tvVipName.setText("欢迎光临 " + personname);
                    handler.sendEmptyMessageDelayed(101, 5000);
                    showface(avatar);
                    lightHelper.SwitchLigth(LightColor.Green);
                }

                if (type == "1") { //陌生人
                    lightHelper.SwitchLigth(LightColor.Red);
                }
            }
            if (message.what == 101) {
                tvState.setText("Open");
            }
            if (message.what == 102) {
                ToastUtil.Show(RtspActivity.this, "服务器连接关闭");
            }
            if (message.what == 103) {
                ToastUtil.Show(RtspActivity.this, "服务器连接错误");
            }
            if (message.what == 200) {

                //弹窗消失
                rlFaceRoot.setVisibility(View.INVISIBLE);
                rlFaceRoot.startAnimation(AnimationUtils.makeOutAnimation(RtspActivity.this, false));
            }
            return false;
        }
    });


    private void getcpu() {

        boolean result = IPHelper.startPing("192.168.8.54");

        String typeName = "";
        Sensor mTempSensor = null;
        StringBuilder sb = new StringBuilder();
        for (Sensor s : allSensors) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                typeName = SensorTypeName.getSensorTypeName(s.getType()) + " " + s.getStringType();
                if (s.getStringType().toUpperCase().indexOf("TEMP") > 0) {
                    mTempSensor = s;
                    break;
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
                    tvTitle.setText("人脸识别通道->" + temperature);

                    if (temperature > cpu_max_temperature) {
                        HwitManager.HwitSetIOValue(4, 1);
                    }
                    if (temperature < cpu_min_temperature) {
                        HwitManager.HwitSetIOValue(4, 0);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void near() {

        diffuseView.setVisibility(View.INVISIBLE);
        diffuseView.stop();
    }

    @Override
    public void leave() {
        diffuseView.setVisibility(View.VISIBLE);
        diffuseView.start();
    }
}
