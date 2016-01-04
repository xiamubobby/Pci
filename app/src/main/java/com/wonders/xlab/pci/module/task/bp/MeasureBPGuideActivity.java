package com.wonders.xlab.pci.module.task.bp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.viewpager.XViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.base.NConnActivity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.FindDeviceOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanEndOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.task.bp.otto.GuideOtto;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeasureBPGuideActivity extends NConnActivity {

    @Bind(R.id.vp_measure_bp_guide)
    XViewPager mVpMeasureBPGuide;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bp_guide;
    }

    @Override
    public String getToolbarTitle() {
        return "血压测量";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        OttoManager.register(this);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        BPGuideOneFragment oneFragment = new BPGuideOneFragment();
        BPGuideTwoFragment twoFragment = new BPGuideTwoFragment();

        mFragmentVPAdapter.addFragment(oneFragment);
        mFragmentVPAdapter.addFragment(twoFragment);
        mVpMeasureBPGuide.setAdapter(mFragmentVPAdapter);
        mVpMeasureBPGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    scan();
                } else if (position == 0) {
                    cancel();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private ProgressDialog mProgressDialog;

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mProgressDialog.setMessage("正在连接血压设备，请稍候...");
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
            showSnackbar(mCoordinator, "连接成功，请开始测量！");
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
            if (mVpMeasureBPGuide != null && mVpMeasureBPGuide.getChildCount() > 0) {
                mVpMeasureBPGuide.setCurrentItem(0);
            }
            showSnackbar(mCoordinator, "连接失败，请重试！");
        }

    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在搜索设备，请稍候...");
//            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    /**
     * 搜索到设备
     *
     * @param otto
     */
    @Subscribe
    public void onFindDevice(FindDeviceOtto otto) {
        if (TextUtils.isEmpty(otto.getDeviceName())) {
            return;
        }

        if (otto.getDeviceName().contains(DEVICE_TYPE.BP.toString())) {
            Log.d("MeasureBPGuideActivity", otto.getDeviceName());
            getData(DEVICE_TYPE.BP, otto.getDeviceAddress());
            if (mProgressDialog != null) {
                mProgressDialog.setMessage("正在配对，请稍候...");
            }
        }
    }

    /**
     * 接收ViewPager中引导页的两个按钮的事件
     *
     * @param otto
     */
    @Subscribe
    public void onGuideStep(GuideOtto otto) {
        switch (otto.getStep()) {
            case 0:
                mVpMeasureBPGuide.setCurrentItem(1);
                break;
            case 1:
                scan();
                break;
        }
    }

    /**
     * 搜索结束
     *
     * @param otto
     */
    @Subscribe
    public void onScanEnd(ScanEndOtto otto) {
        scan();
    }

    public void onDataReceived(BSEntity bsEntity) {
        Log.d("MeasureBPGuideActivity", "bsEntity.getBloodSugar():" + bsEntity.getBloodSugar());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mVpMeasureBPGuide != null && mVpMeasureBPGuide.getCurrentItem() > 0) {
            mVpMeasureBPGuide.setCurrentItem(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
