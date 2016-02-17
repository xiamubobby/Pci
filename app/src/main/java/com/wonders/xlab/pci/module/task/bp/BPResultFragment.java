package com.wonders.xlab.pci.module.task.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BPEntity;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.pci.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.pci.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.mvn.model.IdealRangeModel;
import com.wonders.xlab.pci.module.task.mvn.view.IdealRangeView;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.LoadingDotView;
import me.drakeet.labelview.LabelView;

public class BPResultFragment extends BaseFragment implements MeasureResultView, IdealRangeView {

    @Bind(R.id.tv_bp_result_pressure)
    LabelView mTvBpResultPressure;
    @Bind(R.id.tv_bp_result_pulse_rate)
    LabelView mTvBpResultPulseRate;
    @Bind(R.id.iv_bp_result_bluetooth)
    ImageView mIvBpResultBluetooth;
    @Bind(R.id.ldv_bp_result)
    LoadingDotView mLdvBpResult;
    @Bind(R.id.tv_bp_result_ideal_range)
    TextView mTvBpResultIdealRange;

    private AddRecordModel mRecordModel;
    private IdealRangeModel mIdealRangeModel;

    private Animation rotateAnimation;

    public BPResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordModel = new AddRecordModel(this);
        mIdealRangeModel = new IdealRangeModel(this);
        addModel(mIdealRangeModel);
        addModel(mRecordModel);

        if (rotateAnimation == null) {
            rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        }
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
        mIdealRangeModel.fetchIdealBPRange(AIManager.getInstance(getActivity()).getUserId());
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        startConnectingAnim();
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            startConnectingAnim();

            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BPEntityList bpEntityList) {

        List<BPEntity> bpEntities = bpEntityList.getBp();
        mRecordModel.saveBP(AIManager.getInstance(getActivity()).getUserId(), bpEntityList);

        mTvBpResultPressure.setText(String.format(Locale.CHINA,"%d/%d", bpEntities.get(0).getSystolicPressure(), bpEntities.get(0).getDiastolicPressure()));
        mTvBpResultPulseRate.setText(String.format(Locale.CHINA,"%d", bpEntities.get(0).getHeartRate()));
    }

    @Subscribe
    public void onDeviceHasNoData(EmptyDataOtto otto) {
        stopConnectingAnim();
    }

    @Subscribe
    public void onRequestDataFailed(RequestDataFailed otto) {
        stopConnectingAnim();
    }

    @Override
    public void svSuccess() {
        stopConnectingAnim();
    }

    @Override
    public void svDuplicate() {
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

    /**
     * 底部loading动画
     */
    private void startConnectingAnim() {
        mIvBpResultBluetooth.startAnimation(rotateAnimation);
        mLdvBpResult.setDuration(mLdvBpResult.getChildCount() * 300)
                .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
    }

    private void stopConnectingAnim() {
        mLdvBpResult.stopAnimation();
        mIvBpResultBluetooth.clearAnimation();
    }

    @Override
    public void showRange(String range) {
        mTvBpResultIdealRange.setText(range);
    }

    @Override
    public void fetchIdealRangeFailed(String message) {
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
