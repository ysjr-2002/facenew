package com.visitor.tengli.face;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hwit.HwitManager;
import com.visitor.tengli.face.fs.WebSocketHelper;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.DateUtil;
import com.visitor.tengli.face.util.Light;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RtspActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);
        ButterKnife.bind(this);

        Light.openlight(5);
        initTimerTask();
        initView();
    }

    Timer timer;
    TimerTask timerTask;

    private void initTimerTask() {

        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.d("ysj", DateUtil.getCurrentDateTime());
                int result = HwitManager.HwitGetIrqIOValue(1);
                if (result == 1) {
                    Light.openlight(0);
                }
                else
                {
                    Light.openlight(5);
                }
            }
        };

        timer = new Timer();
        Log.d(Config.tag, DateUtil.getCurrentDateTime());
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

        webSocketHelper = new WebSocketHelper(this, "192.168.0.50", "192.168.0.15", handler);
        webSocketHelper.open();

        Light.openlight(5);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            if (message.what == 100) {

//                if (ActivityCollector.isActivityExist(FaceActivity.class)) {
//                    Log.d("ysj", "showing");
//
//                    FaceActivity test = (FaceActivity) ActivityCollector.getActivity(FaceActivity.class);
//                    if (test != null) {
//                        test.Update();
//                    }
//                    return true;
//                }
//
//                Intent intent = new Intent(getBaseContext(), FaceActivity.class);
//                intent.putExtra("face", message.getData());
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                String personname = message.getData().getString("name");
                name.setText("识别名称->" + personname);
                handler.sendEmptyMessageDelayed(101, 5000);
                Light.openlight(2);
            }
            if (message.what == 101) {

                name.setText("open");
                Light.openlight(0);
            }
            if (message.what == 102) {
                name.setText("close");
            }
            if (message.what == 103) {
                name.setText("error");
            }
            return false;
        }
    });

}
