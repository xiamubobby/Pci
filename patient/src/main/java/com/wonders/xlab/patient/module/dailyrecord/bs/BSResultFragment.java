package com.wonders.xlab.patient.module.dailyrecord.bs;

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
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.RecordSavePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.LoadingDotView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSResultFragment extends BaseFragment implements RecordSavePresenter.RecordSavePresenterListener, IdealRangePresenter.IdealRangePresenterListener {

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

    private IRecordSavePresenter mRecordSavePresenter;
    private IIdealRangePresenter mIdealRangePresenter;

    private Animation rotateAnimation;

    private List<HashMap<String, String>> mPeriodList = new ArrayList<>();

    private boolean mIsSaveSingle = false;

    public BSResultFragment() {
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
        if (mPeriodList.size() == 0) {
            for (int i = 0; i < 7; i++) {
                HashMap<String, String> period = new HashMap<>();
                switch (i) {
                    case 0:
                        period.put("name", "早餐前");
                        break;
                    case 1:
                        period.put("name", "早餐后");
                        break;
                    case 2:
                        period.put("name", "午餐前");
                        break;
                    case 3:
                        period.put("name", "午餐后");
                        break;
                    case 4:
                        period.put("name", "晚餐前");
                        break;
                    case 5:
                        period.put("name", "晚餐后");
                        break;
                    case 6:
                        period.put("name", "睡觉前");
                        break;
                }
                mPeriodList.add(period);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bs_measure_guide_result_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        mIdealRangePresenter.fetchIdealBSRange(AIManager.getInstance(getActivity()).getPatientId());

        mSpBsResultPeriod.setAdapter(new SimpleAdapter(getActivity(), mPeriodList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));
    }

    @OnClick(R.id.btn_bs_result_save)
    public void save() {
        float bsValue = Float.parseFloat(mTvBsResultSugar.getText().toString());

        if (Float.compare(bsValue, 0) > 0 && mTvBsResultSugar.getTag() != null) {
            mIsSaveSingle = true;
            mRecordSavePresenter.saveBSSingle(AIManager.getInstance(getActivity()).getPatientId(), Long.parseLong(mTvBsResultSugar.getTag().toString()), mSpBsResultPeriod.getSelectedItemPosition(), bsValue);
        } else {
            Toast.makeText(getActivity(), "请先测量您的血糖，然后点击保存!", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onScanStart(ScanStartOtto startOtto) {
        startConnectingAnim();
    }

    @Subscribe
    public void onConnectionStatusChanged(ConnStatusOtto connStatusOtto) {
        if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.START) {
            startConnectingAnim();

            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.SUCCESS) {
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BSEntityList bsEntityList) {
        BSEntity bsEntity = bsEntityList.getBs().get(0);
        mTvBsResultSugar.setText(String.valueOf(bsEntity.getBloodSugarValue()));
        mTvBsResultSugar.setTag(bsEntity.getDate());
        mSpBsResultPeriod.setSelection(bsEntity.getTimeIndex());

        bsEntityList.getBs().remove(0);
        if (bsEntityList.getBs().size() > 0) {
            mIsSaveSingle = false;
            mRecordSavePresenter.saveBS(AIManager.getInstance(getActivity()).getPatientId(), bsEntityList);
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
    public void onSaveRecordSuccess(String message) {
        stopConnectingAnim();
        if (mIsSaveSingle) {
            mTvBsResultSugar.setText("0");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("血糖测量(结果)");
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("血糖测量(结果)");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }
}
