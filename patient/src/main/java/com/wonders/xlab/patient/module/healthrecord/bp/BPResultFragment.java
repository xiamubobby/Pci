package com.wonders.xlab.patient.module.healthrecord.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntity;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.RecordSavePresenter;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.LoadingDotView;
import me.drakeet.labelview.LabelView;

public class BPResultFragment extends BaseFragment implements RecordSavePresenter.RecordSavePresenterListener, IdealRangePresenter.IdealRangePresenterListener {

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

    private IRecordSavePresenter mRecordSavePresenter;
    private IIdealRangePresenter mIdealRangePresenter;

    private Animation rotateAnimation;

    public BPResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordSavePresenter = new RecordSavePresenter(this);
        mIdealRangePresenter = new IdealRangePresenter(this);

        addPresenter(mRecordSavePresenter);
        addPresenter(mIdealRangePresenter);

        if (rotateAnimation == null) {
            rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bp_measure_guide_result_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        mIdealRangePresenter.fetchIdealBPRange(AIManager.getInstance(getActivity()).getPatientId());
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
        mRecordSavePresenter.saveBP(AIManager.getInstance(getActivity()).getPatientId(), bpEntityList);

        if (null != bpEntities && bpEntities.size() > 0) {
            mTvBpResultPressure.setText(String.format(Locale.CHINA, "%d/%d", bpEntities.get(0).getSystolicPressure(), bpEntities.get(0).getDiastolicPressure()));
            mTvBpResultPulseRate.setText(String.format(Locale.CHINA, "%d", bpEntities.get(0).getHeartRate()));
        }
    }

    @Subscribe
    public void onDeviceHasNoData(EmptyDataOtto otto) {
        stopConnectingAnim();
    }

    @Subscribe
    public void onRequestDataFailed(RequestDataFailed otto) {
        stopConnectingAnim();
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

    @Override
    public void onSaveRecordSuccess(String message) {
        stopConnectingAnim();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }
}