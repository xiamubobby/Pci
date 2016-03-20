package com.wonders.xlab.patient.module.healthrecord.bp.otto;

/**
 * Created by hua on 16/1/4.
 */
public class GuideOtto {
    /**
     * 0:step 1,go to the second guide page
     * 1:step 2,start startScan device
     */
    private int step;

    public GuideOtto(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
