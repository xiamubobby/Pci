package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.wonders.xlab.pci.doctor.module.bp.bean.BPChartBean;
import com.wonders.xlab.pci.doctor.module.bp.bean.BPListBean;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.BPModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBPModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IBPPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class BPPresenter extends BasePresenter implements IBPModel {
    private IBPPresenter mBloodPressurePresenter;
    private BPModel mBPModel;

    public BPPresenter(@NonNull IBPPresenter bloodPressurePresenter) {
        mBloodPressurePresenter = bloodPressurePresenter;

        mBPModel = new BPModel(this);
        addModel(mBPModel);
    }

    public void getBPList() {
//        mBPModel.getBPList();
        onReceiveBPSuccess(null);
    }

    @Override
    public void onReceiveBPSuccess(BPEntity bpEntity) {
        ArrayList<BPListBean> bpListBeanList = new ArrayList<>();
        ArrayList<BPChartBean> systolicList = new ArrayList<>();
        ArrayList<BPChartBean> diastolicList = new ArrayList<>();
        ArrayList<BPChartBean> heartRateList = new ArrayList<>();
        String[] xVals = new String[180];

        long headerId = 0;
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                headerId++;
            }
            BPListBean bean = new BPListBean();
            bean.setHeaderId(headerId);
            bean.setDiastolic("1" + i);
            bean.setSystolic("2" + i);
            bean.setHeartRate(i + "0");
            bean.setTime("2016-02-0" + i);
            bpListBeanList.add(bean);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -180);

        for (int i = 0; i < 180; i++) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            xVals[i] = month + "-" + day;
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            BPChartBean systolicBean = new BPChartBean();
            systolicBean.setValue(getRandom(90f, 10f));
            systolicList.add(systolicBean);

            BPChartBean heartRateBean = new BPChartBean();
            heartRateBean.setValue(getRandom(100f, 50f));
            heartRateList.add(heartRateBean);

            BPChartBean diastolicBean = new BPChartBean();
            diastolicBean.setValue(getRandom(140f, 50f));
            diastolicList.add(diastolicBean);
        }

        CombinedData data = new CombinedData(xVals);

        data.setData(generateLineData(systolicList, diastolicList, xVals));
        data.setData(generateScatterData(heartRateList, xVals));

        mBloodPressurePresenter.showBloodPressureList(bpListBeanList, data);
    }

    @Override
    public void onReceiveFailed(String message) {

    }

    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    private LineData generateLineData(ArrayList<BPChartBean> systolicList, ArrayList<BPChartBean> diastolicList, String[] mChartXVals) {

        ArrayList<LineDataSet> dataSets = new ArrayList<>();

        for (int z = 0; z < 2; z++) {

            ArrayList<Entry> values = new ArrayList<>();
            LineDataSet d;

            String label = "";
            int color = 0;
            switch (z) {
                case 0:
                    for (int i = 0; i < systolicList.size(); i++) {
                        values.add(new Entry(systolicList.get(i).getValue(), i));
                    }

                    label = "舒张压";
                    color = Color.parseColor("#12b9f8");
                    break;
                case 1:
                    for (int i = 0; i < diastolicList.size(); i++) {
                        values.add(new Entry(diastolicList.get(i).getValue(), i));
                    }

                    label = "收缩压";
                    color = Color.parseColor("#69c88e");
                    break;
            }

            d = new LineDataSet(values, "");
            d.setLineWidth(2.5f);
            d.setCircleSize(4f);
            d.setLabel(label);
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        return new LineData(mChartXVals, dataSets);
    }

    protected ScatterData generateScatterData(ArrayList<BPChartBean> heartRateList, String[] mChartXVals) {

        ScatterData d = new ScatterData(mChartXVals);

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < heartRateList.size(); index++)
            entries.add(new Entry(heartRateList.get(index).getValue(), index));

        ScatterDataSet set = new ScatterDataSet(entries, "心率");
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setColor(Color.parseColor("#de0404"));
        set.setScatterShapeSize(7.5f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);

        d.addDataSet(set);

        return d;
    }
}