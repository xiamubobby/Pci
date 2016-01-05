package com.wonders.xlab.pci.module.task.bs;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

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
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.men_measure_retry:
                        connectBondedDevice();
                        break;
                }
                return false;
            }
        });

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        BSGuideOneFragment oneFragment = new BSGuideOneFragment();
        BSGuideTwoFragment twoFragment = new BSGuideTwoFragment();
        BSResultFragment threeFragment = new BSResultFragment();

        mFragmentVPAdapter.addFragment(oneFragment);
        mFragmentVPAdapter.addFragment(twoFragment);
        mFragmentVPAdapter.addFragment(threeFragment);
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
                    switch (mVpMeasureBSGuide.getCurrentItem()) {
                        case 2:
                            if (!mBluetoothAdapter.isEnabled()) {
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(enableBtIntent, NConnActivity.REQUEST_ENABLE_BOND);
                            } else {
                                connectBondedDevice();
                            }
                            getToolbar().inflateMenu(R.menu.menu_measure_retry);
                            break;
                        default:
                            getToolbar().getMenu().clear();
                            dismissDialog();
                            cancel();
                    }
                }
            }
        });
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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
            mProgressDialog.setMessage("连接成功，正在读取数据，请稍候...");
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
                if (mVpMeasureBSGuide != null) {
                    mVpMeasureBSGuide.setCurrentItem(2);
                }
                break;
        }
    }

    @Subscribe
    public void onDataReceived(BSEntity bsEntity) {
        cancel();
        dismissDialog();
    }

    private void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
        if (mVpMeasureBSGuide != null && mVpMeasureBSGuide.getCurrentItem() > 0) {
            mVpMeasureBSGuide.setCurrentItem(mVpMeasureBSGuide.getCurrentItem() - 1);
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
