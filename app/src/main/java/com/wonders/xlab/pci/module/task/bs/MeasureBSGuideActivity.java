package com.wonders.xlab.pci.module.task.bs;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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

public class MeasureBSGuideActivity extends NConnActivity {

    @Bind(R.id.vp_measure_bs_guide)
    XViewPager mVpMeasureBSGuide;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private FragmentVPAdapter mFragmentVPAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    public int getContentLayout() {
        return R.layout.activity_measure_bs_guide;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖测量";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        OttoManager.register(this);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        BSGuideOneFragment oneFragment = new BSGuideOneFragment();
        BSGuideTwoFragment twoFragment = new BSGuideTwoFragment();

        mFragmentVPAdapter.addFragment(oneFragment);
        mFragmentVPAdapter.addFragment(twoFragment);
        mVpMeasureBSGuide.setAdapter(mFragmentVPAdapter);
        mVpMeasureBSGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mVpMeasureBSGuide.getCurrentItem() == 1) {
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, NConnActivity.REQUEST_ENABLE_BOND);
                        } else {
                            connectBondedDevice();
                        }
                    } else {
                        if (mProgressDialog != null) {
                            mProgressDialog.hide();
                        }
                        cancel();
                    }
                }
            }
        });
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setCancelable(false);
        }
    }

    @Override
    public DEVICE_TYPE getDeviceType() {
        return DEVICE_TYPE.BS;
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mProgressDialog.setMessage("正在连接血糖设备，请稍候...");
            mProgressDialog.show();
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
            showSnackbar(mCoordinator, "连接成功，请开始测量！", true);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mProgressDialog.setMessage("连接失败，即将重新搜索设备");
            postDelayScan(5000);
        }
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        mProgressDialog.setMessage("正在搜索设备，请稍候...");
        mProgressDialog.show();
    }

    /**
     * 搜索结束
     *
     * @param otto
     */
    @Subscribe
    public void onScanEnd(ScanEndOtto otto) {
        postDelayScan(5000);
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

        if (otto.getDeviceName().contains(DEVICE_TYPE.BS.toString())) {
            Log.d("MeasureBSGuideActivity", otto.getDeviceName());
            getData(otto.getDeviceAddress());
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
                mVpMeasureBSGuide.setCurrentItem(1);
                break;
            case 1:
                connectBondedDevice();
                break;
        }
    }

    @Subscribe
    public void onDataReceived(BSEntity bsEntity) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        Log.d("MeasureBPGuideActivity", "bsEntity.getBloodSugar():" + bsEntity.getBloodSugar());
    }

    @Override
    public void onBackPressed() {
        if (mVpMeasureBSGuide != null && mVpMeasureBSGuide.getCurrentItem() > 0) {
            mVpMeasureBSGuide.setCurrentItem(0);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}