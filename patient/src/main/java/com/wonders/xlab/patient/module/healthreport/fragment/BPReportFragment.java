package com.wonders.xlab.patient.module.healthreport.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.healthreport.adapter.BPReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.module.healthreport.otto.ShowMeasureChooseDialogOtto;
import com.wonders.xlab.patient.mvp.presenter.IBPReportPresenter;
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.BPReportCachePresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IdealRangePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.CommonRecyclerView;

/**
 * 今日血压
 */
public class BPReportFragment extends BaseFragment implements BPReportCachePresenter.BPReportCachePresenterListener, IdealRangePresenter.IdealRangePresenterListener {

    private IBPReportPresenter mBPReportPresenter;
    private IIdealRangePresenter mIdealRangePresenter;

    @Bind(R.id.text_blood_pressure_report_ideal_range)
    TextView mTextIdealRange;
    @Bind(R.id.recycler_view_blood_pressure_report)
    CommonRecyclerView mRecyclerView;

    private BPReportAdapter mBPReportAdapter;

    public BPReportFragment() {
        // Required empty public constructor
    }

    public static BPReportFragment newInstance() {
        return new BPReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBPReportPresenter = new BPReportCachePresenter(this);
        mIdealRangePresenter = new IdealRangePresenter(this);
        addPresenter(mIdealRangePresenter);
        addPresenter(mBPReportPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.bp_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String rangeStr = SPManager.get(getActivity()).getString(Constant.PREF_KEY_IDEAL_BP_RANGE, "");
        if (TextUtils.isEmpty(rangeStr)) {
            mIdealRangePresenter.fetchIdealBPRange(AIManager.getInstance(getActivity()).getPatientId());
        } else {
            mTextIdealRange.setText(rangeStr);
        }
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        TextView tvMeasure = (TextView) mRecyclerView.findViewById(R.id.tv_bp_bs_report_empty_measure);
        tvMeasure.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OttoManager.post(new ShowMeasureChooseDialogOtto(0));
            }
        });

        mBPReportPresenter.getBPCacheList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Subscribe
    public void refresh(BPSaveSuccessOtto otto) {
        mBPReportPresenter.getBPCacheList(AIManager.getInstance(getActivity()).getPatientId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void showBPList(List<BPReportBean> beanList) {
        mRecyclerView.showRecyclerView();
        if (null == mBPReportAdapter) {
            mBPReportAdapter = new BPReportAdapter();
        }
        mBPReportAdapter.setDatas(beanList);
        mRecyclerView.setAdapter(mBPReportAdapter);
    }

    @Override
    public void showEmptyView() {
        mRecyclerView.showEmptyView();
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRange(String range) {
        mTextIdealRange.setText(range);
    }
}
