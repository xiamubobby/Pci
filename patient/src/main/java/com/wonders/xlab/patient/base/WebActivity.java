package com.wonders.xlab.patient.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends AppbarActivity {
    public final static String EXTRA_WEB_URL = "webUrl";
    @Bind(R.id.wb_web)
    WebView mWbWeb;

    @Override
    public int getContentLayout() {
        return R.layout.web_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "打开链接失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Bundle data = intent.getExtras();

        String webUrl = data.getString(EXTRA_WEB_URL);

        initWebView(webUrl);
    }

    private void initWebView(final String url) {

        WebSettings webSettings = mWbWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        MyWebChromeClient webChromeClient = new MyWebChromeClient();

        mWbWeb.setWebViewClient(new MyWebViewClient());
        mWbWeb.setWebChromeClient(webChromeClient);
        mWbWeb.loadUrl(url);
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setToolbarTitle(title);
        }
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWbWeb != null && mWbWeb.canGoBack()) {
            mWbWeb.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
