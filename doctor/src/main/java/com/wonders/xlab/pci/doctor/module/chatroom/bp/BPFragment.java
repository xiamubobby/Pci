package com.wonders.xlab.pci.doctor.module.chatroom.bp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.adapter.BPRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.bean.BPListBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.presenter.IBloodPressurePresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.bp.presenter.impl.BloodPressurePresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BPFragment extends BaseFragment implements BloodPressurePresenter.BloodPressurePresenterListener {
    public static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.chart_blood_pressure)
    CombinedChart mChart;

    private IBloodPressurePresenter mBPPresenter;
    private BPRVAdapter mBPRVAdapter;

    private final int itemMaxX = 30;

    @Bind(R.id.recycler_view_blood_pressure)
    CommonRecyclerView mRecyclerView;

    public BPFragment() {
        // Required empty public constructor
    }

    public static BPFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        BPFragment fragment = new BPFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bp_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChart();

        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBPPresenter.getBPList(mPatientId, false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBPPresenter.getBPList(mPatientId, true);
            }
        });

        mBPPresenter = new BloodPressurePresenter(this);
        addPresenter(mBPPresenter);

        mRecyclerView.setRefreshing(true);
        mBPPresenter.getBPList(mPatientId, true);
    }

    private void initChart() {
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDescription("血压(mmHg)");
        mChart.setNoDataText("暂无数据");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(true);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        addLimitLines(leftAxis);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        BPMarkerView mv = new BPMarkerView(this, R.layout.custom_marker_view);
//        mChart.setMarkerView(mv);

    }

    @Override
    public void showBloodPressureList(ArrayList<BPListBean> bpListBeanList, CombinedData combinedData) {
        initAdapter();
        mBPRVAdapter.setDatas(bpListBeanList);

        mChart.setData(combinedData);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);
        // limit the number of visible entries
        mChart.setVisibleXRangeMaximum(itemMaxX);
        // move to the latest entry
        mChart.moveViewToX(combinedData.getXValCount() - itemMaxX - 1);
    }

    @Override
    public void appendBloodPressureList(ArrayList<BPListBean> bpListBeanList) {
        initAdapter();
        mBPRVAdapter.appendDatas(bpListBeanList);
    }

    private void initAdapter() {
        if (mBPRVAdapter == null) {
            mBPRVAdapter = new BPRVAdapter();
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBPRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(decoration);
            mBPRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    decoration.invalidateHeaders();
                }
            });
            mRecyclerView.setAdapter(mBPRVAdapter);
        }
    }

    private void addLimitLines(YAxis leftAxis) {
        LimitLine ll1 = new LimitLine(140f, "高压(高)");
        ll1.setLineWidth(1f);
        ll1.setLineColor(Color.parseColor("#69c88e"));
        ll1.setTextColor(Color.parseColor("#69c88e"));
        ll1.enableDashedLine(5f, 5f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(8f);

        LimitLine ll2 = new LimitLine(91f, "高压(低)");
        ll2.setLineColor(Color.parseColor("#69c88e"));
        ll2.setTextColor(Color.parseColor("#69c88e"));
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(5f, 5f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(8f);

        LimitLine ll3 = new LimitLine(90f, "低压(高)");
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#12b9f8"));
        ll3.setTextColor(Color.parseColor("#12b9f8"));
        ll3.enableDashedLine(5f, 5f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        ll3.setTextSize(8f);

        LimitLine ll4 = new LimitLine(60f, "低压(低)");
        ll4.setLineColor(Color.parseColor("#12b9f8"));
        ll4.setTextColor(Color.parseColor("#12b9f8"));
        ll4.setLineWidth(1f);
        ll4.enableDashedLine(5f, 5f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll4.setTextSize(8f);

        LimitLine ll5 = new LimitLine(85f, "心率(高)");
        ll5.setLineColor(Color.parseColor("#de0404"));
        ll5.setTextColor(Color.parseColor("#de0404"));
        ll5.setLineWidth(1f);
        ll5.enableDashedLine(5f, 5f, 0f);
        ll5.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll5.setTextSize(8f);

        LimitLine ll6 = new LimitLine(50, "心率(低)");
        ll6.setLineColor(Color.parseColor("#de0404"));
        ll6.setTextColor(Color.parseColor("#de0404"));
        ll6.setLineWidth(1f);
        ll6.enableDashedLine(5f, 5f, 0f);
        ll6.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll6.setTextSize(8f);

        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);
        leftAxis.addLimitLine(ll5);
        leftAxis.addLimitLine(ll6);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
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
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBPRVAdapter = null;
    }
}