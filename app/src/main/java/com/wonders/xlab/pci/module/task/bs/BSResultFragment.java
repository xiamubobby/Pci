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
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntityList;
import com.wonders.xlab.pci.assist.connection.otto.ConnStatusOtto;
import com.wonders.xlab.pci.assist.connection.otto.EmptyDataOtto;
import com.wonders.xlab.pci.assist.connection.otto.RequestDataFailed;
import com.wonders.xlab.pci.assist.connection.otto.ScanStartOtto;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.base.mvn.view.MeasureResultView;
import com.wonders.xlab.pci.module.task.mvn.model.AddRecordModel;
import com.wonders.xlab.pci.module.task.mvn.model.IdealRangeModel;
import com.wonders.xlab.pci.module.task.mvn.view.IdealRangeView;

import butterknife.Bind;
import butterknife.ButterKnife;
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
//    @Bind(R.id.sp_bs_result_period)
//    Spinner mSpBsResultPeriod;

    private AddRecordModel mAddRecordModel;
    private IdealRangeModel mIdealRangeModel;

    private Animation rotateAnimation;

//    private List<HashMap<String, String>> mPeriodList = new ArrayList<>();

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
/*
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
        }*/
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

//        mSpBsResultPeriod.setAdapter(new SimpleAdapter(getActivity(), mPeriodList, R.layout.item_spinner_text, new String[]{"name"}, new int[]{R.id.tv_spinner}));
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
        //save cache again that save to server failed last time
       /* List<BSAAModel> cache = new Select().from(BSAAModel.class).execute();
        for (BSAAModel model : cache) {
            BSEntity bsEntity = new BSEntity();
            bsEntity.setBSModel(model);
            bsEntityList.getBs().add(bsEntity);
        }*/

        BSEntity bsEntity = bsEntityList.getBs().get(0);
        mTvBsResultSugar.setText(String.valueOf(bsEntity.getBloodSugarValue()));
//        mSpBsResultPeriod.setSelection(bsEntity.getTimeIndex());

        //cache
        /*for (BSEntity entity : bsEntityList.getBs()) {
            BSAAModel model = new BSAAModel();
            model.setBSEntity(entity);
            model.save();
        }*/

//        bsEntityList.getBs().remove(0);
        mAddRecordModel.saveBS(AIManager.getInstance(getActivity()).getUserId(), bsEntityList);
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
//        new Delete().from(BSAAModel.class).execute();
    }

    @Override
    public void svDuplicate() {
        //the datas are saved successfully, delete the cache
//        new Delete().from(BSAAModel.class).execute();
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
//        mIdealRangeModel.fetchIdealBSRange(AIManager.getInstance(getActivity()).getUserId());
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
}
