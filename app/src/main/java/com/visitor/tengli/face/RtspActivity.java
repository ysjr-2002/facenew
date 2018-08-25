package com.visitor.tengli.face;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwit.HwitManager;
import com.squareup.picasso.Picasso;
import com.visitor.tengli.face.fs.WebSocketHelper;
import com.visitor.tengli.face.util.DateUtil;
import com.visitor.tengli.face.util.Light;
import com.visitor.tengli.face.util.LightColorEnum;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RtspActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.image_face)
    CircleImageView imageFace;
    @BindView(R.id.tvopen)
    TextView tvopen;
    @BindView(R.id.rl_face_root)
    RelativeLayout rlFaceRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);
        ButterKnife.bind(this);

        initTimerTask();
        initView();
    }

    private void showface(String avatar) {

//        String avatar = "http://pic-bucket.nosdn.127.net/photo/0003/2018-08-25/DQ1OVQN700AJ0003NOS.jpg";
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

    Timer timer;
    TimerTask timerTask;

    private void initTimerTask() {

        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.d("ysj", DateUtil.getCurrentDateTime());
                int result = Light.getFace();
                if (result == 1) {
                    Light.openlight(LightColorEnum.White);
                } else {
                    Light.openlight(LightColorEnum.Close);
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
    }

    WebSocketHelper webSocketHelper;

    private void initView() {

        webview.setWebViewClient(new WebViewClient() {

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }

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
//        webview.loadUrl("http://www.baidu.com");
        webSocketHelper = new WebSocketHelper(this, "192.168.0.50", "192.168.0.13", handler);
        webSocketHelper.open();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if (message.what == 100) {

                String personname = message.getData().getString("name");
                String avatar = message.getData().getString("avatar");
                name.setText("识别名称->" + personname);
                handler.sendEmptyMessageDelayed(101, 5000);
                Light.openlight(LightColorEnum.Green);

                showface(avatar);
            }
            if (message.what == 101) {

                name.setText("open");
                Light.openlight(LightColorEnum.White);
            }
            if (message.what == 102) {
                name.setText("close");
            }
            if (message.what == 103) {
                name.setText("error");
//                showface();
            }
            if (message.what == 200) {

                rlFaceRoot.setVisibility(View.INVISIBLE);
                rlFaceRoot.startAnimation(AnimationUtils.makeOutAnimation(RtspActivity.this,false));
            }
            return false;
        }
    });

}
