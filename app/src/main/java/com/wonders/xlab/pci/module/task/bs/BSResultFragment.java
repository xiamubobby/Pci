package com.wonders.xlab.pci.module.task.bs;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.LoadingDotView;
import me.drakeet.labelview.LabelView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSResultFragment extends BaseFragment {


    @Bind(R.id.tv_bs_result_sugar)
    LabelView mTvBsResultSugar;
    @Bind(R.id.iv_bs_result_bluetooth)
    ImageView mIvBsResultBluetooth;
    @Bind(R.id.ldv_bs_result)
    LoadingDotView mLdvBsResult;

    public BSResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bs_result, container, false);
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
        mLdvBsResult.setDuration(mLdvBsResult.getChildCount() * 300)
                .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mLdvBsResult.setDuration(mLdvBsResult.getChildCount() * 300)
                    .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mLdvBsResult.stopAnimation();
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BSEntity bsEntity) {
        mTvBsResultSugar.setText(String.valueOf(bsEntity.getBloodSugar()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
