package com.wonders.xlab.pci.doctor.module.chatroom.indicator.adapter.bean;

import android.databinding.ObservableField;

import java.util.List;

/**
 * Created by jimmy on 16/4/27.
 * 检验指标
 */

public class TestIndicatorBean {
    /**
     * 检验时间
     */
    private ObservableField<String> testTimeInStr = new ObservableField<>();
    /**
     * 医院名称
     */
    private ObservableField<String> hospitalName = new ObservableField<>();
    /**
     * 科室名称
     */
    private ObservableField<String> departmentsName = new ObservableField<>();
    /**
     * 指标列表
     */
    private ObservableField<List<IndicatorBean>> indicatorList = new ObservableField<>();

    public static class IndicatorBean {

        String item;
        String result;
        String referenceValue;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getReferenceValue() {
            return referenceValue;
        }

        public void setReferenceValue(String referenceValue) {
            this.referenceValue = referenceValue;
        }
    }


    public ObservableField<String> getTestTimeInStr() {
        return testTimeInStr;
    }

    public ObservableField<String> getHospitalName() {
        return hospitalName;
    }

    public ObservableField<String> getDepartmentsName() {
        return departmentsName;
    }

    public ObservableField<List<IndicatorBean>> getIndicatorList() {
        return indicatorList;
    }
}
