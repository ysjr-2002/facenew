package com.visitor.tengli.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwit.HwitManager;
import com.squareup.picasso.Picasso;
import com.visitor.tengli.face.core.IFaceListener;
import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.fs.WebSocketHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.util.FileUtils;
import com.visitor.tengli.face.util.LightColor;
import com.visitor.tengli.face.util.SensorTypeName;
import com.visitor.tengli.face.util.ToastUtil;
import com.visitor.tengli.face.view.DiffuseView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CAMERA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.KOALA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.STRANGER;

public class RtspActivity extends BaseActivity implements IFaceListener {

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
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    @BindView(R.id.diffuseView)
    DiffuseView diffuseView;

    String koala;
    String camera;
    boolean bStranger;
    @Inject
    SharedPreferencesHelper sp;
    @Inject
    LightHelper lightHelper;

    WebSocketHelper webSocketHelper;
    @BindView(R.id.imageview_openstate)
    ImageView imageviewOpenstate;

    @Override
    int getLayout() {
        return R.layout.activity_rtsp;
    }


    //600 998 0.75 120

    @Override
    void create() {
        koala = sp.getStringValue(KOALA_IP, "");
        camera = sp.getStringValue(CAMERA_IP, "");
        bStranger = sp.getBooleanValue(STRANGER, false);
        initView();

        lightHelper.setFaceListerner(this);
        lightHelper.setContext(this);
        lightHelper.run();
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        webview.loadUrl("http://127.0.0.1:8080/browserfs.html");
        webSocketHelper = new WebSocketHelper(this, koala, camera, handler);
        webSocketHelper.open();

        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;
        float scale = this.getResources().getDisplayMetrics().scaledDensity;
        int qq = this.getResources().getDisplayMetrics().densityDpi;
//        ToastUtil.Show(this, "" + width + " " + height + " " + scale + " " + qq);
    }

    Bitmap strangerBitmap = null;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if (message.what == 100) {

                Bundle bundle = message.getData();
                String type = bundle.getString("type");
                if (type == "0") {
                    String personname = bundle.getString("name");
                    String avatar = bundle.getString("avatar");
                    imageviewOpenstate.setImageResource(R.mipmap.yz);
                    tvVipName.setText("欢迎光临 " + personname);
                    tvopen.setText("请通行");
                    showface(avatar);
                    lightHelper.SwitchLigth(LightColor.Green);
                    handler.sendEmptyMessageDelayed(101, 5000);
                }

                if (type == "1") { //陌生人
                    if (strangerBitmap != null) {
                        strangerBitmap.recycle();
                        strangerBitmap = null;
                        System.gc();
                    }

                    imageviewOpenstate.setImageResource(R.mipmap.msr);
                    tvVipName.setText("未登记信息");
                    tvopen.setText("禁止通行");
                    String avatar = bundle.getString("avatar");
                    strangerBitmap = FileUtils.stringToBitmap(avatar);
                    imageFace.setImageBitmap(strangerBitmap);
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

    @Override
    public void near() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                diffuseView.setVisibility(View.INVISIBLE);
                diffuseView.stop();
            }
        });
    }

    @Override
    public void leave() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                diffuseView.setVisibility(View.VISIBLE);
                diffuseView.start();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
