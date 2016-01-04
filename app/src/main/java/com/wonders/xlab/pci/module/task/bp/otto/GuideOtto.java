package com.wonders.xlab.pci.module.task.bp.otto;

/**
 * Created by hua on 16/1/4.
 */
public class GuideOtto {
    /**
     * 0:step 1,go to the second guide page
     * 1:step 2,start scan device
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
