package com.wonders.xlab.patient.module.dailyreport.datarecord.bp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.viewpager.XViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.assist.deviceconnection.base.NConnActivity;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.FindDeviceOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ScanEndOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.patient.module.dailyreport.datarecord.bp.otto.GuideOtto;
import com.wonders.xlab.patient.util.UmengEventId;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 健康设备测量血压
 */
public class BPGuideActivity extends NConnActivity{

    @Bind(R.id.vp_measure_bp_guide)
    XViewPager mVpMeasureBPGuide;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private FragmentVPAdapter mFragmentVPAdapter;

    private ProgressDialog mProgressDialog;

    @Override
    public int getContentLayout() {
        return R.layout.bp_measure_guide_activity;
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
        BPResultFragment threeFragment = new BPResultFragment();

        mFragmentVPAdapter.addFragment(oneFragment);
        mFragmentVPAdapter.addFragment(twoFragment);
        mFragmentVPAdapter.addFragment(threeFragment);
        mVpMeasureBPGuide.setAdapter(mFragmentVPAdapter);
        mVpMeasureBPGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    switch (mVpMeasureBPGuide.getCurrentItem()) {
                        case 2:
                            if (null != mBluetoothAdapter) {
                                if (!mBluetoothAdapter.isEnabled()) {
                                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                    startActivityForResult(enableBtIntent, NConnActivity.REQUEST_ENABLE_BOND);
                                } else {
                                    connectBondedDevice();
                                }
                                getToolbar().getMenu().clear();
                                getToolbar().inflateMenu(R.menu.menu_measure_result_retry);
                            }

                            break;
                        default:
                            getToolbar().getMenu().clear();
                            getToolbar().inflateMenu(R.menu.menu_measure_direct);
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
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel();
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "停止连接", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancel();
                }
            });
        }
    }

    @Override
    public DEVICE_TYPE getDeviceType() {
        return DEVICE_TYPE.BP;
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {

        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mProgressDialog.setMessage("正在连接血压仪，请稍候...");
            mProgressDialog.show();
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mProgressDialog.setMessage("连接成功，正在读取数据，请稍候...");
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mProgressDialog.setMessage("正在搜索设备，请稍候...");
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

        if (otto.getDeviceName().contains(DEVICE_TYPE.BP.toString())) {
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
                mVpMeasureBPGuide.setCurrentItem(1);
                break;
            case 1:
                if (mVpMeasureBPGuide != null) {
                    mVpMeasureBPGuide.setCurrentItem(2);
                }
                break;
        }
    }

    @Subscribe
    public void onDataReceived(BPEntityList bpEntityList) {
        dismissDialog();
    }

    @Subscribe
    public void onDeviceHasNoData(EmptyDataOtto otto) {
        cancel();
        dismissDialog();
        showShortToast("没有读取到血压数据，请先测量血压，然后重新同步数据");
    }

    @Subscribe
    public void onRequestDataFailed(RequestDataFailed otto) {
        cancel();
        dismissDialog();
        showShortToast(otto.getMessage());
    }

    @Override
    public void onBackPressed() {
        if (mVpMeasureBPGuide != null && mVpMeasureBPGuide.getCurrentItem() > 0) {
            mVpMeasureBPGuide.setCurrentItem(mVpMeasureBPGuide.getCurrentItem() - 1);
            return;
        }
        super.onBackPressed();
    }

    public void onPause() {
        super.onPause();
        cancel();
        dismissDialog();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();

        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    private void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_measure_direct,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_measure_retry:
                connectBondedDevice();
                //TODO umeng
                MobclickAgent.onEvent(this, UmengEventId.HOME_DAILY_RECORD_MEASURE_RETRY_BP);
                break;
            case R.id.menu_measure_direct:
                OttoManager.post(new GuideOtto(1));
                //TODO umeng
                MobclickAgent.onEvent(this, UmengEventId.HOME_DAILY_RECORD_MEASURE_DIRECT_BP);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

}
