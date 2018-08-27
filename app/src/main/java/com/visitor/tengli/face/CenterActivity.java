package com.visitor.tengli.face;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CenterActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        ButterKnife.bind(this);


        initWebView();
    }

    private void initWebView() {


        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                    view.loadUrl(url);
                return true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient(){

        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(true);

        webview.loadUrl("http://www.baidu.com");
    }
}
