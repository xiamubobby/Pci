package com.wonders.xlab.patient.module.dailyreport.otto;

/**
 * Created by hua on 16/3/25.
 */
public class ShowMeasureChooseDialogOtto {
    public static final int TYPE_BP = 0;
    public static final int TYPE_BS = 1;
    /**
     * 0:blood pressure
     * 1:blood sugar
     */
    private int measureType;

    public ShowMeasureChooseDialogOtto(int measureType) {
        this.measureType = measureType;
    }

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }
}
