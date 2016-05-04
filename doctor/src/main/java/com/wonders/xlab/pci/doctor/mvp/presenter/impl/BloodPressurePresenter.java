package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import android.graphics.Color;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IBloodPressureModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.BloodPressureModel;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodpressure.bean.BPChartBean;
import com.wonders.xlab.pci.doctor.module.patientinfo.bloodpressure.bean.BPListBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IBloodPressurePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/4/1.
 */
public class BloodPressurePresenter extends BasePagePresenter implements IBloodPressurePresenter, BloodPressureModel.BPModelListener {
    private BloodPressurePresenterListener mListener;
    private IBloodPressureModel mBloodPressureModel;

    public BloodPressurePresenter(BloodPressurePresenterListener listener) {
        mListener = listener;
        mBloodPressureModel = new BloodPressureModel(this);
        addModel(mBloodPressureModel);
    }

    @Override
    public void getBPList(String patientId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mListener.hideLoading();
            return;
        }
        mBloodPressureModel.getBPList(patientId, 0, Calendar.getInstance().getTimeInMillis(), getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveBPSuccess(BPEntity bpEntity) {
        mListener.hideLoading();

        if (null == bpEntity.getRet_values() || null == bpEntity.getRet_values().getLine() || null == bpEntity.getRet_values().getTable()) {
            mListener.showNetworkError("获取血压数据失败，请重试！");
            return;
        }

        /**
         * table
         */
        BPEntity.RetValuesEntity.TableEntity tableEntity = bpEntity.getRet_values().getTable();

        //1
        updatePageInfo(tableEntity.getNumber(), tableEntity.isFirst(), tableEntity.isLast());

        ArrayList<BPListBean> bpListBeanList = new ArrayList<>();

        List<BPEntity.RetValuesEntity.TableEntity.ContentEntity> contentEntityList = tableEntity.getContent();
        for (int i = 0; i < contentEntityList.size(); i++) {
            BPEntity.RetValuesEntity.TableEntity.ContentEntity contentEntity = contentEntityList.get(i);
            BPListBean bean = new BPListBean();

            bean.setDiastolic(String.valueOf(contentEntity.getDiastolicPressure()));
            bean.setSystolic(String.valueOf(contentEntity.getSystolicPressure()));
            bean.setHeartRate(String.valueOf(contentEntity.getHeartRate()));
            bean.setRecordTimeInMill(contentEntity.getRecordTime());
            bpListBeanList.add(bean);
        }

        if (shouldAppend()) {
            mListener.appendBloodPressureList(bpListBeanList);
            return;
        }

        /**
         * line
         */
        BPEntity.RetValuesEntity.LineEntity lineEntity = bpEntity.getRet_values().getLine();

        ArrayList<BPChartBean> systolicList = new ArrayList<>();
        ArrayList<BPChartBean> diastolicList = new ArrayList<>();
        ArrayList<BPChartBean> heartRateList = new ArrayList<>();

        int chartSize = lineEntity.getDiastolic().size();
        String[] xVals = new String[chartSize];

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -chartSize);

        for (int i = 0; i < chartSize; i++) {
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int month = calendar.get(Calendar.MONTH) + 1;
            xVals[i] = DateUtil.format(lineEntity.getRecordTime().get(i), "MM/dd");
//            calendar.add(Calendar.DAY_OF_MONTH, 1);

            BPChartBean systolicBean = new BPChartBean();
            systolicBean.setValue(lineEntity.getSystolic().get(i));
            systolicList.add(systolicBean);

            BPChartBean heartRateBean = new BPChartBean();
            heartRateBean.setValue(lineEntity.getHeartRates().get(i));
            heartRateList.add(heartRateBean);

            BPChartBean diastolicBean = new BPChartBean();
            diastolicBean.setValue(lineEntity.getDiastolic().get(i));
            diastolicList.add(diastolicBean);
        }

        CombinedData data = new CombinedData(xVals);

        data.setData(generateLineData(systolicList, diastolicList, xVals));
        data.setData(generateScatterData(heartRateList, xVals));

        mListener.showBloodPressureList(bpListBeanList, data);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mListener.hideLoading();
        mListener.showNetworkError(message);
    }

    /**
     * 组装血压线图数据
     *
     * @param systolicList
     * @param diastolicList
     * @param mChartXVals
     * @return
     */
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
                    label = "高压";
                    color = Color.parseColor("#69c88e");
                    break;
                case 1:
                    for (int i = 0; i < diastolicList.size(); i++) {
                        values.add(new Entry(diastolicList.get(i).getValue(), i));
                    }
                    label = "低压";
                    color = Color.parseColor("#12b9f8");
                    break;
            }

            d = new LineDataSet(values, "");
            d.setLineWidth(1.5f);
            d.setCircleSize(4f);
            d.setLabel(label);
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        return new LineData(mChartXVals, dataSets);
    }

    /**
     * 组装心率点图数据
     *
     * @param heartRateList
     * @param mChartXVals
     * @return
     */
    private ScatterData generateScatterData(ArrayList<BPChartBean> heartRateList, String[] mChartXVals) {

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

    public interface BloodPressurePresenterListener extends BasePresenterListener {
        void showBloodPressureList(ArrayList<BPListBean> bpListBeanList, CombinedData combinedData);

        void appendBloodPressureList(ArrayList<BPListBean> bpListBeanList);
    }
}
