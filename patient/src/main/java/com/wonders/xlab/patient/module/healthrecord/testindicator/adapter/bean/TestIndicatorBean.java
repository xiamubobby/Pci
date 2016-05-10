package com.wonders.xlab.patient.module.healthrecord.testindicator.adapter.bean;

import java.util.List;

/**
 * Created by jimmy on 16/4/27.
 * 检验指标
 */

public class TestIndicatorBean {
    /**
     * 检验时间
     */
    private String testTimeInStr;
    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 科室名称
     */
    private int departmentsName;
    /**
     * 指标列表
     */
    private List<IndicatorBean> indicatorList;

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


    public String getTestTimeInStr() {
        return testTimeInStr;
    }

    public void setTestTimeInStr(String testTimeInStr) {
        this.testTimeInStr = testTimeInStr;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentsName() {
        switch (departmentsName) {
            case 1:
                return "普通门诊";
            case 2:
                return "专科门诊";
            case 3:
                return "专家门诊";
            case 4:
                return "特需门诊";
            case 5:
                return "转病门诊";
            case 6:
                return "急诊";
            case 7:
                return "体检";
            case 8:
                return "其他";
            default:
                return "其他";
        }
    }

    public void setDepartmentsName(int departmentsName) {
        this.departmentsName = departmentsName;
    }

    public List<IndicatorBean> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<IndicatorBean> indicatorList) {
        this.indicatorList = indicatorList;
    }
}
