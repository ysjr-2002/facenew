package com.visitor.tengli.face;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.visitor.tengli.face.core.IFaceListener;
import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.fs.WebSocketHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.util.FileUtils;
import com.visitor.tengli.face.util.LightColor;
import com.visitor.tengli.face.util.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CAMERA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.CLOSEGATE_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.GREEN_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.KOALA_IP;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.RED_DELAY;
import static com.visitor.tengli.face.helpers.SharedPreferencesHelper.STRANGER;
import static com.visitor.tengli.face.util.Config.PASSWORD_MAX_LENGTH;
import static com.visitor.tengli.face.util.Config.SETTING_PASSWORD;

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
//    @BindView(R.id.diffuseView)
//    DiffuseView diffuseView;


    @Inject
    SharedPreferencesHelper sp;
    @Inject
    LightHelper lightHelper;

    WebSocketHelper webSocketHelper;
    @BindView(R.id.imageview_openstate)
    ImageView imageviewOpenstate;
    @BindView(R.id.layout_password_container)
    RelativeLayout layoutPasswordContainer;

    @BindView(R.id.et_pwd)
    TextView mEtPwd;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    int getLayout() {
        return R.layout.activity_rtsp;
    }

    String koala;
    String camera;
    boolean bStranger;
    int green_delay = 0;
    int red_delay = 0;
    int closegate_delay = 0;
    //600 998 0.75 120


    @Override
    void create() {

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);

        koala = sp.getStringValue(KOALA_IP, "");
        camera = sp.getStringValue(CAMERA_IP, "");
        green_delay = Integer.parseInt(sp.getStringValue(GREEN_DELAY, "2000"));
        red_delay = Integer.parseInt(sp.getStringValue(RED_DELAY, "2000"));
        closegate_delay = Integer.parseInt(sp.getStringValue(CLOSEGATE_DELAY, "2000"));
        bStranger = sp.getBooleanValue(STRANGER, false);
        initView();

        lightHelper.setFaceListerner(this);
        lightHelper.setContext(this);
        lightHelper.setLigth_green_to_white(green_delay);
        lightHelper.setLigth_red_to_white(red_delay);
        lightHelper.run();

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutPasswordContainer.setVisibility(View.VISIBLE);
            }
        });

        llRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ivSetting.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
        //5秒后，隐藏Setting
        handler.sendEmptyMessageDelayed(202, 5000);
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lightHelper.stop();
        webSocketHelper.HandClose();
//        diffuseView.stop();
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
                    lightHelper.open();
                    handler.sendEmptyMessageDelayed(201, closegate_delay);
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

                    rlFaceRoot.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(RtspActivity.this, R.anim.big);
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
            }
            if (message.what == 101) {

            }
            if (message.what == 102) {
                ToastUtil.show("服务器连接关闭");
            }
            if (message.what == 103) {
                ToastUtil.show("服务器连接错误");
            }
            if (message.what == 200) {

                //弹窗消失
                rlFaceRoot.setVisibility(View.INVISIBLE);
                rlFaceRoot.startAnimation(AnimationUtils.makeOutAnimation(RtspActivity.this, false));
            }
            if (message.what == 201) { //关闸
                lightHelper.close();
            }
            if (message.what == 202) {
                ivSetting.setVisibility(View.INVISIBLE);
            }
            return false;
        }
    });

    @Override
    public void near() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                diffuseView.setVisibility(View.INVISIBLE);
//                diffuseView.stop();
            }
        });
    }

    @Override
    public void leave() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                diffuseView.setVisibility(View.VISIBLE);
//                diffuseView.start();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    String mPassword = "";

    @OnClick({R.id.layout_key_i, R.id.layout_key_ii, R.id.layout_key_iii, R.id.layout_key_iv, R.id.layout_key_v, R.id.layout_key_vi, R.id.layout_key_vii, R.id.layout_key_viii, R.id.layout_key_ix, R.id.layout_key_x})
    public void onNumberClicked(View view) {


        mPassword += ((TextView) ((LinearLayout) view).getChildAt(0)).getText();
        mEtPwd.setText(String.format(getString(R.string.key_password_format), mEtPwd.getText()));
    }

    int error_count = 0;

    @OnClick({R.id.layout_key_confirm, R.id.layout_key_delete})
    public void onActionClicked(View view) {

        switch (view.getId()) {
            case R.id.layout_key_delete:
                if (TextUtils.isEmpty(mPassword)) {
                    return;
                }
                mPassword = mPassword.substring(0, mPassword.length() - 1);
                mEtPwd.setText(mEtPwd.getText().toString().substring(0, mPassword.length()));
                break;
            case R.id.layout_key_confirm:

                if (mPassword.length() != PASSWORD_MAX_LENGTH) {
                    ToastUtil.show(String.format(getString(R.string.key_password_length_too_short), PASSWORD_MAX_LENGTH));
                    return;
                }

                if (mPassword.equals(SETTING_PASSWORD)) {
                    mPassword = null;
                    mEtPwd.setText("");
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                    this.finish();
                } else {
                    ToastUtil.show("密码错误！");
                    error_count++;

                    if (error_count == 4) { //错误次数超过三次
                        mPassword = null;
                        mEtPwd.setText("");
                        layoutPasswordContainer.setVisibility(View.INVISIBLE);
                    }
                }
        }
    }
}
