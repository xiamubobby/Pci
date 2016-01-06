package com.wonders.xlab.pci.module.task.bs;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.wonders.xlab.pci.assist.connection.aamodel.BSAAModel;
import com.wonders.xlab.pci.assist.connection.entity.BSEntity;
import com.wonders.xlab.pci.assist.connection.entity.BSEntityList;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BSResultFragment extends BaseFragment implements MeasureResultView {

    @Bind(R.id.tv_bs_result_sugar)
    LabelView mTvBsResultSugar;
    @Bind(R.id.iv_bs_result_bluetooth)
    ImageView mIvBsResultBluetooth;
    @Bind(R.id.ldv_bs_result)
    LoadingDotView mLdvBsResult;

    private AddRecordModel mAddRecordModel;

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
        mAddRecordModel = new AddRecordModel(this);
        addModel(mAddRecordModel);
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
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth);
        } else if (connStatusOtto.getStatus() == ConnStatusOtto.STATUS.FAILED) {
            mIvBsResultBluetooth.setImageResource(R.drawable.ic_bluetooth_failed);
        }
    }

    @Subscribe
    public void onDataReceived(BSEntityList bsEntityList) {
        //save cache again that save to server failed last time
        List<BSAAModel> cache = new Select().from(BSAAModel.class).execute();
        for (BSAAModel model : cache) {
            BSEntity bsEntity = new BSEntity();
            bsEntity.setBSModel(model);
            bsEntityList.getBs().add(bsEntity);
        }

        mAddRecordModel.saveBS(AIManager.getInstance(getActivity()).getUserId(), bsEntityList);
        mTvBsResultSugar.setText(String.valueOf(bsEntityList.getBs().get(0).getBloodSugar()));
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
    public void svSuccess() {
        mLdvBsResult.stopAnimation();
        new Delete().from(BSAAModel.class).exists();
    }

    @Override
    public void svDuplicate() {
        //the datas are saved successfully, delete the cache
        new Delete().from(BSAAModel.class).exists();
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
}
