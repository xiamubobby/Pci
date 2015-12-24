package com.wonders.xlab.pci.module.record;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthPlanFragment extends BaseFragment {

    @Bind(R.id.wv_health_plan)
    WebView mWvHealthPlan;

    public HealthPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_plan, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView(Constant.HEALTH_PLAN_URL + AIManager.getInstance(getActivity()).getUserId());
    }

    private void initWebView(final String url) {

        mWvHealthPlan.getSettings().setJavaScriptEnabled(true);
        mWvHealthPlan.getSettings().setDomStorageEnabled(true);
        mWvHealthPlan.getSettings().setDatabaseEnabled(true);
        mWvHealthPlan.getSettings().setAppCacheEnabled(false);
        mWvHealthPlan.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWvHealthPlan.getSettings().setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 16) {
            mWvHealthPlan.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        mWvHealthPlan.setWebViewClient(new MyWebViewClient());
        mWvHealthPlan.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
