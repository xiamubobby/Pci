package com.wonders.xlab.pci.module.record;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.otto.ConnectStateBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthPlanActivity extends AppbarActivity {

    @Bind(R.id.wv_health_plan)
    WebView mWvHealthPlan;
    @Bind(R.id.refresh_health_plan)
    SwipeRefreshLayout mRefresh;

    @Override
    public int getContentLayout() {
        return R.layout.activity_health_plan;
    }

    @Override
    public String getToolbarTitle() {
        return "健康方案";
    }

    public HealthPlanActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWebView();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mWvHealthPlan != null) {
                    mWvHealthPlan.reload();
                }
            }
        });
        OttoManager.register(this);
    }

    private void initWebView() {

        mWvHealthPlan.getSettings().setJavaScriptEnabled(true);
        mWvHealthPlan.getSettings().setDomStorageEnabled(true);
        mWvHealthPlan.getSettings().setDatabaseEnabled(true);
        mWvHealthPlan.getSettings().setAppCacheEnabled(true);
        mWvHealthPlan.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        mWvHealthPlan.getSettings().setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 16) {
            mWvHealthPlan.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        mWvHealthPlan.setWebViewClient(new MyWebViewClient());
        mWvHealthPlan.loadUrl(Constant.HEALTH_PLAN_URL + AIManager.getInstance(this).getUserTel());
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mWvHealthPlan.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            mWvHealthPlan.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mRefresh.post(new Runnable() {
                @Override
                public void run() {
                    if (mRefresh != null) {
                        mRefresh.setRefreshing(false);
                    }
                }
            });
        }
    }

    @Subscribe
    public void onConnectionChanged(ConnectStateBus bean) {
        if (bean.isConnected()) {
            mWvHealthPlan.loadUrl(Constant.HEALTH_PLAN_URL + AIManager.getInstance(this).getUserTel());
            mWvHealthPlan.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWvHealthPlan.setVisibility(View.VISIBLE);
                }
            }, 500);
        } else {
            Snackbar.make(mWvHealthPlan, getResources().getString(R.string.network_disconnected), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
