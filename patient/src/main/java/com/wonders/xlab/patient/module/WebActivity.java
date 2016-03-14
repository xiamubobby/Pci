package com.wonders.xlab.patient.module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;

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
        webSettings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        mWbWeb.setWebViewClient(new MyWebViewClient());

        mWbWeb.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        boolean mIsPageFinished = true;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mIsPageFinished = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setToolbarTitle(view.getTitle());
            mIsPageFinished = true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

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

}
