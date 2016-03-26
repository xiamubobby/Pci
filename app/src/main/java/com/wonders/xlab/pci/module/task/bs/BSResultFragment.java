package com.wonders.xlab.pci.module.task.bs;


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
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntity;
import com.wonders.xlab.pci.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.pci.assist.deviceconnection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.deviceconnection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.deviceconnection.otto.RequestDataFailed;
import com.wonders.xlab.pci.assist.deviceconnection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.mvn.model.IdealRangeModel;
import com.wonders.xlab.pci.module.task.mvn.view.IdealRangeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.uikit.LoadingDotView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSResultFragment extends BaseFragment implements MeasureResultView, IdealRangeView {

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

    private AddRecordModel mAddRecordModel;
    private IdealRangeModel mIdealRangeModel;

    private Animation rotateAnimation;

    private List<HashMap<String, String>> mPeriodList = new ArrayList<>();

    private boolean mIsSaveSingle = false;

    public BSResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddRecordModel = new AddRecordModel(this);
        mIdealRangeModel = new IdealRangeModel(this);
        addModel(mIdealRangeModel);
        addModel(mAddRecordModel);

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
        View view = inflater.inflate(R.layout.fragment_bs_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
        mIdealRangeModel.fetchIdealBSRange(AIManager.getInstance(getActivity()).getUserId());

        mSpBsResultPeriod.setAdapter(new SimpleAdapter(getActivity(), mPeriodList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));
    }

    @OnClick(R.id.btn_bs_result_save)
    public void save() {
        float bsValue = Float.parseFloat(mTvBsResultSugar.getText().toString());

        if (Float.compare(bsValue, 0) > 0 && mTvBsResultSugar.getTag() != null) {
            mIsSaveSingle = true;
            mAddRecordModel.saveBSSingle(AIManager.getInstance(getActivity()).getUserId(), Long.parseLong(mTvBsResultSugar.getTag().toString()), mSpBsResultPeriod.getSelectedItemPosition(), bsValue);
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
            mAddRecordModel.saveBS(AIManager.getInstance(getActivity()).getUserId(), bsEntityList);
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
    public void svSuccess() {
        stopConnectingAnim();
        if (mIsSaveSingle) {
            mTvBsResultSugar.setText("0");
            Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
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

    @Override
    public void showRange(String range) {
        mTvBsResultIdealRange.setText(range);
    }

    @Override
    public void fetchIdealRangeFailed(String message) {
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
}
