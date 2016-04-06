package com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntity;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.patient.mvp.presenter.IBPSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BPSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IdealRangePresenter;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.LoadingDotView;
import im.hua.utils.DateUtil;
import me.drakeet.labelview.LabelView;

public class BPResultFragment extends BaseFragment implements IdealRangePresenter.IdealRangePresenterListener, BPSavePresenter.RecordSavePresenterListener {

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

    private IBPSavePresenter mIBPSavePresenter;
    private IIdealRangePresenter mIdealRangePresenter;

    private Animation rotateAnimation;

    public BPResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (rotateAnimation == null) {
            rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bp_measure_guide_result_fragment, container, false);
        ButterKnife.bind(this, view);

        mIBPSavePresenter = new BPSavePresenter(this);
        mIdealRangePresenter = new IdealRangePresenter(this);

        addPresenter(mIBPSavePresenter);
        addPresenter(mIdealRangePresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);

        String rangeStr = SPManager.get(getActivity()).getString(Constant.PREF_KEY_IDEAL_BP_RANGE, "");
        if (TextUtils.isEmpty(rangeStr)) {
            mIdealRangePresenter.fetchIdealBPRange(AIManager.getInstance().getPatientId());
        } else {
            mTvBpResultIdealRange.setText(rangeStr);
        }
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        startConnectingAnim();
    }

    /**
     * TODO umeng
     * 友盟统计使用，统计从连接到成功得到服务器返回的花费时间
     */
    private long mStartConnectTime;//从开始连接设备的时间,单位：毫秒
    private int mConnectFailedTime = 0;//连接设备失败次数
    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            mStartConnectTime = Calendar.getInstance().getTimeInMillis();

            startConnectingAnim();
            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            //TODO umeng
            mConnectFailedTime = 0;
            long nowTime = Calendar.getInstance().getTimeInMillis();
            MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_COST_TIME_BP, null, (int) (nowTime - mStartConnectTime) / 1000);

            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            //TODO umeng
            mConnectFailedTime++;

            mIvBpResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BPEntityList bpEntityList) {

        if (null == bpEntityList || null == bpEntityList.getBp() || bpEntityList.getBp().size() <= 0) {
            showShortToast("未读取到测量数据，请重新测量！");
            return;
        }

        List<BPEntity> bpEntities = bpEntityList.getBp();

        BPEntity bpEntity = bpEntities.get(0);
        mTvBpResultPressure.setText(String.format(Locale.CHINA, "%d/%d", bpEntity.getSystolicPressure(), bpEntity.getDiastolicPressure()));
        mTvBpResultPulseRate.setText(String.format(Locale.CHINA, "%d", bpEntity.getHeartRate()));
        mIBPSavePresenter.saveBPSingle(AIManager.getInstance().getPatientId(), bpEntity.getDate(), bpEntity.getHeartRate(), bpEntity.getSystolicPressure(), bpEntity.getDiastolicPressure());

        bpEntities.remove(0);
        if (bpEntities.size() > 0) {
            mIBPSavePresenter.saveBP(AIManager.getInstance().getPatientId(), bpEntityList);
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
        rotateAnimation.cancel();
        mIvBpResultBluetooth.clearAnimation();
    }

    @Override
    public void showRange(String range) {
        SPManager.get(getActivity()).putString(Constant.PREF_KEY_IDEAL_BP_RANGE, range);
        mTvBpResultIdealRange.setText(range);
    }

    @Override
    public void showError(String message) {
        stopConnectingAnim();
    }

    @Override
    public void hideLoading() {
        rotateAnimation.cancel();
    }

    @Override
    public void onSaveBPSuccess(String message) {
        //TODO umeng
        long nowTime = Calendar.getInstance().getTimeInMillis();
        MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_COST_TIME_BP, null, (int) (nowTime - mStartConnectTime) / 1000);

        stopConnectingAnim();
        showShortToast(message);
        getActivity().finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bp_guide_result));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bp_guide_result));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnectFailedTime > 0) {
            HashMap<String, String> map = new HashMap<>();
            map.put("patientId", AIManager.getInstance().getPatientId());
            map.put("failedTime", DateUtil.format(Calendar.getInstance().getTimeInMillis(),"yyyy-MM-dd HH:mm:ss"));
            MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_FAILED_TIME_BP,map,mConnectFailedTime);
        }
    }
}
