package com.visitor.tengli.face;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.visitor.tengli.face.di.component.DaggerMyComponent;
import com.visitor.tengli.face.di.module.MyModule;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;
import com.visitor.tengli.face.model.Student;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.ToastUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CenterActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.button)
    Button button;

    @Override
    int getLayout() {
        return R.layout.activity_center;
    }

    @Override
    void create() {
        //DaggerMyComponent.builder().myModule(new MyModule(getApplication())).build().inject(this);
        String name = student1.getName();
        String x = student1.toString();
        String y = student1.toString();

        if (x.equals(y)) {
            String ok = "";
        }

        String koala = sharedPreferencesHelper.getStringValue(SharedPreferencesHelper.KOALA_IP, "");
        String temp = "";

        cpu();
    }

    private void cpu() {

        String path = "/sys/class/thermal/thermal_zone";
        for (int i = 0; i <= 7; i++) {

            String a = path + i + "/type";
            String b = path + i + "/temp";

            String temp = readfile(b);
            int x = Integer.parseInt(temp) / 1000;
            String c = "type->" + readfile(a) + " temperature->" + x;
            Log.d(Config.tag, c);
        }
    }

    private String readfile(String path) {
        File file = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            String temp = "";
            inputStream.close();
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            String ok = ex.getMessage();
            ex.printStackTrace();
        }
        return "shit";
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Inject
    public Student student1;

    @Inject
    public Student student2;

    @Inject
    public SharedPreferencesHelper sharedPreferencesHelper;

    private void initWebView() {


        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                view.loadUrl(url);
                return true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {

        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);

        webview.loadUrl("http://www.baidu.com");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        int[] a = {1, 2, 3};
        int len = a.length;

        Context context1 = getApplication().getBaseContext();
        Context context2 = getApplicationContext();
        if (context1.equals(context2)) {
            String x = "";
        }
        ToastUtil.Show(context1, "are you ok???");
    }
}
