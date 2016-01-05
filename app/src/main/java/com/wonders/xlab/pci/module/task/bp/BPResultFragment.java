package com.wonders.xlab.pci.module.task.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.entity.BPEntity;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.LoadingDotView;
import me.drakeet.labelview.LabelView;

public class BPResultFragment extends BaseFragment {

    @Bind(R.id.tv_bp_result_pressure)
    LabelView mTvBpResultPressure;
    @Bind(R.id.tv_bp_result_pulse_rate)
    LabelView mTvBpResultPulseRate;
    @Bind(R.id.iv_bp_result_bluetooth)
    ImageView mIvBpResultBluetooth;
    @Bind(R.id.ldv_bp_result)
    LoadingDotView mLdvBpResult;

    public BPResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bp_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        mLdvBpResult.setDuration(mLdvBpResult.getChildCount() * 300)
                .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mLdvBpResult.setDuration(mLdvBpResult.getChildCount() * 300)
                    .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mLdvBpResult.stopAnimation();
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mLdvBpResult.stopAnimation();
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BPEntity bpEntity) {
        mLdvBpResult.stopAnimation();
        mTvBpResultPressure.setText(String.format("%d/%d", bpEntity.getHighPressure(), bpEntity.getLowPressure()));
        mTvBpResultPulseRate.setText(String.format("%d", bpEntity.getPulseRate()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
