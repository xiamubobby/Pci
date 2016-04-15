package com.wonders.xlab.patient.module.main.home.dailyreport.datarecord.bs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntity;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.patient.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.patient.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.patient.mvp.presenter.IBSSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BSSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IdealRangePresenter;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.LoadingDotView;
import im.hua.utils.DateUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSResultFragment extends BaseFragment implements BSSavePresenter.BSSavePresenterListener, IdealRangePresenter.IdealRangePresenterListener {

    @Bind(R.id.tv_bs_result_sugar)
    TextView mTvBsResultSugar;
    @Bind(R.id.iv_bs_result_bluetooth)
    ImageView mIvBsResultBluetooth;
    @Bind(R.id.ldv_bs_result)
    LoadingDotView mLdvBsResult;
    @Bind(R.id.tv_bs_result_ideal_range)
    TextView mTvBsResultIdealRange;
    @Bind(R.id.sp_bs_result_period)
    Spinner mSpBsResultPeriod;

    private IBSSavePresenter mRecordSavePresenter;
    private IIdealRangePresenter mIdealRangePresenter;

    private Animation rotateAnimation;

    private boolean mIsSaveSingle = false;

    public BSResultFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bs_measure_guide_result_fragment, container, false);
        ButterKnife.bind(this, view);

        mRecordSavePresenter = new BSSavePresenter(this);
        mIdealRangePresenter = new IdealRangePresenter(this);
        addPresenter(mRecordSavePresenter);
        addPresenter(mIdealRangePresenter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        mRecordSavePresenter.getBSPeriodDic();

        mIdealRangePresenter.fetchIdealBSRange(AIManager.getInstance().getPatientId());
    }

    @OnClick(R.id.btn_bs_result_save)
    public void save() {
        float bsValue = Float.parseFloat(mTvBsResultSugar.getText().toString());

        if (Float.compare(bsValue, 0) > 0 && mTvBsResultSugar.getTag() != null) {
            mIsSaveSingle = true;
            mRecordSavePresenter.saveBSSingle(AIManager.getInstance().getPatientId(), Long.parseLong(mTvBsResultSugar.getTag().toString()), mSpBsResultPeriod.getSelectedItemPosition(), bsValue);
        } else {
            showShortToast("请先测量您的血糖，然后点击保存!");
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
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            //TODO umeng
            mConnectFailedTime = 0;
            long nowTime = Calendar.getInstance().getTimeInMillis();
            MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_COST_TIME_BS, null, (int) (nowTime - mStartConnectTime) / 1000);

            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            //TODO umeng
            mConnectFailedTime++;

            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BSEntityList bsEntityList) {
        if (null == bsEntityList || null == bsEntityList.getBs() || bsEntityList.getBs().size() <= 0) {
            showShortToast("未读取到测量数据，请重新测量！");
            return;
        }
        BSEntity bsEntity = bsEntityList.getBs().get(0);
        mTvBsResultSugar.setText(String.valueOf(bsEntity.getBloodSugarValue()));
        mTvBsResultSugar.setTag(bsEntity.getDate());
        mSpBsResultPeriod.setSelection(bsEntity.getTimeIndex());

        bsEntityList.getBs().remove(0);
        if (bsEntityList.getBs().size() > 0) {
            mIsSaveSingle = false;
            mRecordSavePresenter.saveBS(AIManager.getInstance().getPatientId(), bsEntityList);
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

    @Override
    public void onSaveBSSuccess(String message) {
        //TODO umeng
        long nowTime = Calendar.getInstance().getTimeInMillis();
        MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_COST_TIME_BS, null, (int) (nowTime - mStartConnectTime) / 1000);

        stopConnectingAnim();
        if (mIsSaveSingle) {
            mTvBsResultSugar.setText("0");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
    }

    @Override
    public void showBSPeriodDicList(List<String> periodList, int currentPeriodIndex) {
        List<HashMap<String, String>> mPeriodList = new ArrayList<>();

        for (String periodStr : periodList) {
            HashMap<String, String> period = new HashMap<>();
            period.put("name", periodStr);
            mPeriodList.add(period);
        }
        mSpBsResultPeriod.setAdapter(new SimpleAdapter(getActivity(), mPeriodList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));
        mSpBsResultPeriod.setSelection(currentPeriodIndex);
    }

    @Override
    public void showRange(String range) {
        mTvBsResultIdealRange.setText(range);
    }

    /**
     * 底部loading动画
     */
    private void startConnectingAnim() {
        mIvBsResultBluetooth.startAnimation(rotateAnimation);
        mLdvBsResult.setDuration(mLdvBsResult.getChildCount() * 300)
                .setDotColor(Color.BLACK).setDotSize(18).startAnimation(0);
    }

    private void stopConnectingAnim() {
        mIvBsResultBluetooth.clearAnimation();
        mLdvBsResult.stopAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bs_guide_result));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bs_guide_result));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnectFailedTime > 0) {
            HashMap<String, String> map = new HashMap<>();
            map.put("patientId", AIManager.getInstance().getPatientId());
            map.put("failedTime", DateUtil.format(Calendar.getInstance().getTimeInMillis(), "yyyy-MM-dd HH:mm:ss"));
            MobclickAgent.onEventValue(getActivity(), UmengEventId.HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_FAILED_TIME_BS, map, mConnectFailedTime);
        }
    }
}
