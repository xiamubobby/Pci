package com.wonders.xlab.patient.module.dailyrecord.otto;

/**
 * Created by hua on 16/1/6.
 */
public class CanModifyMedicineOtto {
    private boolean mCanModify;

    public CanModifyMedicineOtto(boolean canModify) {
        mCanModify = canModify;
    }

    public boolean isCanModify() {
        return mCanModify;
    }

    public void setCanModify(boolean canModify) {
        mCanModify = canModify;
    }
}
