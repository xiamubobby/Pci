package com.wonders.xlab.pci.module.task.bp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.wonders.xlab.pci.assist.connection.entity.BPEntity;
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

    private ProgressDialog mProgressDialog;

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

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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
                /*if (position == 1) {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {
                        connectBondedDevice();
                    }
                } else if (position == 0) {
                    cancel();
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mVpMeasureBPGuide.getCurrentItem() == 1) {
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
        return DEVICE_TYPE.BP;
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {

        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mProgressDialog.setMessage("正在连接血压设备，请稍候...");
            mProgressDialog.show();
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
            showSnackbar(mCoordinator, "连接成功，开始读取数据！", true);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            if (mProgressDialog != null) {
                mProgressDialog.hide();
            }
            /*if (mVpMeasureBPGuide != null && mVpMeasureBPGuide.getChildCount() > 0) {
                mVpMeasureBPGuide.setCurrentItem(0);
            }*/
            showSnackbar(mCoordinator, "连接失败，请重试！", true);
        }
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        mProgressDialog.setMessage("正在搜索设备，请稍候...");
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
                connectBondedDevice();
                break;
        }
    }

//    private long mLastScanTime = 0;

    /**
     * 搜索结束
     *
     * @param otto
     */
    @Subscribe
    public void onScanEnd(ScanEndOtto otto) {
        /*long nowTime = Calendar.getInstance().getTimeInMillis();
        if (nowTime - mLastScanTime > 10000) {
            mLastScanTime = nowTime;
           */
//        startScan();
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startScan();
            }
        }, 5000);
    }

    @Subscribe
    public void onDataReceived(BPEntity bpEntity) {
        Log.d("MeasureBPGuideActivity", "bsEntity.getBloodSugar():" + bpEntity.getPulseRate());
    }

    @Override
    public void onBackPressed() {
        if (mVpMeasureBPGuide != null && mVpMeasureBPGuide.getCurrentItem() > 0) {
            mVpMeasureBPGuide.setCurrentItem(0);
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
