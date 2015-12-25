package com.wonders.xlab.pci.module.record;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.rxbus.ConnectStateBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthPlanFragment extends BaseFragment {

    @Bind(R.id.wv_health_plan)
    WebView mWvHealthPlan;
    private CompositeSubscription mSubscription;

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
        initWebView();

        initRxBusListener();
    }

    private void initWebView() {

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
        mWvHealthPlan.loadUrl(Constant.HEALTH_PLAN_URL + AIManager.getInstance(getActivity()).getUserId());
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
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mWvHealthPlan.setVisibility(View.INVISIBLE);
        }
    }

    private void initRxBusListener() {
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();

            mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    if (o instanceof ConnectStateBus) {
                        ConnectStateBus connectStateBus = (ConnectStateBus) o;
                        if (connectStateBus.isConnected()) {
                            mWvHealthPlan.loadUrl(Constant.HEALTH_PLAN_URL + AIManager.getInstance(getActivity()).getUserId());
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
                }
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
        ButterKnife.unbind(this);
    }
}
