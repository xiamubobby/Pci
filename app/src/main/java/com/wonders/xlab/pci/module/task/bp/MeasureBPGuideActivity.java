package com.wonders.xlab.pci.module.task.bp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
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
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.assist.connection.base.NConnActivity;
import com.wonders.xlab.pci.assist.connection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.connection.otto.FindDeviceOtto;
import com.wonders.xlab.pci.assist.connection.otto.RequestDataFailed;
import com.wonders.xlab.pci.assist.connection.otto.ScanEndOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.bp.otto.GuideOtto;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.otto.TaskRefreshOtto;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MeasureBPGuideActivity extends NConnActivity implements MeasureResultView {

    @Bind(R.id.vp_measure_bp_guide)
    XViewPager mVpMeasureBPGuide;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private FragmentVPAdapter mFragmentVPAdapter;

    private ProgressDialog mProgressDialog;

    private AddRecordModel mAddRecordModel;

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

        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);

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
            if (BuildConfig.DEBUG) Log.d("MeasureBPGuideActivity", otto.getDeviceName());
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
        cancel();
//        dismissDialog();
        mAddRecordModel.saveBP(AIManager.getInstance(this).getUserId(),bpEntityList);
    }

    @Subscribe
    public void onDeviceHasNoData(EmptyDataOtto otto) {
        cancel();
        dismissDialog();
        showSnackbar(mCoordinator,"没有读取到血压数据，请先测量血压，然后重新同步数据",true);
    }

    @Subscribe
    public void onRequestDataFailed(RequestDataFailed otto) {
        cancel();
        dismissDialog();
        showSnackbar(mCoordinator,otto.getMessage(),true);
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
    public void svSuccess() {
        RxBus.getInstance().send(new TaskRefreshOtto());
        showSnackbar(mCoordinator,"保存成功",true);
    }

    @Override
    public void svDuplicate() {

    }

    @Override
    public void svFailed(String message) {
        showSnackbar(mCoordinator,"保存失败，请重试!",true);
    }

    @Override
    public void svShowLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("正在保存数据，请稍候...");
        }
    }

    @Override
    public void svHideLoading() {
        dismissDialog();
    }
}
