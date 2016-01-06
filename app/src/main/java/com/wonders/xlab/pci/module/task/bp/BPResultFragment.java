package com.wonders.xlab.pci.module.task.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.assist.connection.entity.BPEntity;
import com.wonders.xlab.pci.assist.connection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.connection.aamodel.BPAAModel;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.LoadingDotView;
import me.drakeet.labelview.LabelView;

public class BPResultFragment extends BaseFragment implements MeasureResultView {

    @Bind(R.id.tv_bp_result_pressure)
    LabelView mTvBpResultPressure;
    @Bind(R.id.tv_bp_result_pulse_rate)
    LabelView mTvBpResultPulseRate;
    @Bind(R.id.iv_bp_result_bluetooth)
    ImageView mIvBpResultBluetooth;
    @Bind(R.id.ldv_bp_result)
    LoadingDotView mLdvBpResult;

    private AddRecordModel mRecordModel;

    public BPResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bp_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        mRecordModel = new AddRecordModel(this);
        addModel(mRecordModel);
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
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BPEntityList bpEntityList) {
        //save cache again that save to server failed last time
        List<BPAAModel> cache = new Select().from(BPAAModel.class).execute();
        for (BPAAModel model : cache) {
            BPEntity bpEntity = new BPEntity();
            bpEntity.setBPModel(model);
            bpEntityList.getBp().add(bpEntity);
        }

        List<BPEntity> bpEntities = bpEntityList.getBp();
        mRecordModel.saveBP(AIManager.getInstance(getActivity()).getUserId(), bpEntityList);

        mTvBpResultPressure.setText(String.format("%d/%d", bpEntities.get(0).getSystolicPressure(), bpEntities.get(0).getDiastolicPressure()));
        mTvBpResultPulseRate.setText(String.format("%d", bpEntities.get(0).getHeartRate()));
    }

    @Override
    public void svSuccess() {
        mLdvBpResult.stopAnimation();
        //the datas are saved successfully, delete the cache
        new Delete().from(BPAAModel.class).exists();
    }

    @Override
    public void svDuplicate() {
        //the datas are saved successfully, delete the cache
        new Delete().from(BPAAModel.class).exists();
    }

    @Override
    public void svFailed(String message) {

    }

    @Override
    public void svShowLoading() {

    }

    @Override
    public void svHideLoading() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("血压测量(结果)");
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("血压测量(结果)");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
