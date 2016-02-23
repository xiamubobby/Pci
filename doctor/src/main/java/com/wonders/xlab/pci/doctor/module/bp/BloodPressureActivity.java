package com.wonders.xlab.pci.doctor.module.bp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.bp.adapter.BPRVAdapter;
import com.wonders.xlab.pci.doctor.module.bp.bean.BPBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.BloodPressurePresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IBloodPressurePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BloodPressureActivity extends AppbarActivity implements IBloodPressurePresenter {

    @Bind(R.id.chart_blood_pressure)
    CombinedChart mChart;

    private int[] mColors = new int[]{
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    private BloodPressurePresenter mBloodPressurePresenter;
    private BPRVAdapter mBPRVAdapter;

    private final int mItemCount = 180;
    private final int itemMaxX = 30;
    protected String[] xVals = new String[mItemCount];

    @Bind(R.id.recycler_view_blood_pressure)
    PullLoadMoreRecyclerView mRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.blood_pressure_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "血压";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRecyclerView.setLinearLayout();
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));

        mBloodPressurePresenter = new BloodPressurePresenter(this);
        addPresenter(mBloodPressurePresenter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -180);
        for (int i = 0; i < mItemCount; i++) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            xVals[i] = month + "-" + day;

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mBloodPressurePresenter.getBloodPressureList();
        mChart.setPinchZoom(true);
    }

    @Override
    public void showBloodPressureList(List<BPBean> bpBeanList) {
        if (mBPRVAdapter == null) {
            mBPRVAdapter = new BPRVAdapter();
            mRecyclerView.setAdapter(mBPRVAdapter);
            final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(mBPRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(decoration);
            mBPRVAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    decoration.invalidateHeaders();
                }
            });
        }

        mBPRVAdapter.setDatas(bpBeanList);

        mChart.setDescription("血压(mmHg)");
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

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        CombinedData data = new CombinedData(xVals);

        data.setData(generateLineData(5));
        data.setData(generateScatterData());

        mChart.setData(data);
        mChart.invalidate();
        mChart.animateXY(2000, 2000);

        // limit the number of visible entries
        mChart.setVisibleXRangeMaximum(itemMaxX);
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);

        // move to the latest entry
        mChart.moveViewToX(data.getXValCount() - itemMaxX - 1);
    }

    @Override
    public void showError(String message) {

    }

    private LineData generateLineData(int start) {

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        for (int z = 0; z < 2; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < mItemCount; i++) {
                double val = getRandom(90, start);
                values.add(new Entry((float) val, i));
            }

            LineDataSet d = new LineDataSet(values, "");
            d.setLineWidth(2.5f);
            d.setCircleSize(4f);

            String label = "";
            int color = mColors[z % mColors.length];
            switch (z) {
                case 0:
                    label = "舒张压";
                    color = Color.parseColor("#12b9f8");
                    break;
                case 1:
                    label = "收缩压";
                    color = Color.parseColor("#69c88e");
                    break;
            }
            d.setLabel(label);
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        return new LineData(xVals, dataSets);
    }

    protected ScatterData generateScatterData() {

        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < mItemCount; index++)
            entries.add(new Entry(getRandom(20, 15), index));

        ScatterDataSet set = new ScatterDataSet(entries, "心率");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setColor(Color.parseColor("#de0404"));
        set.setScatterShapeSize(7.5f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        d.addDataSet(set);

        return d;
    }

    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
