package com.wonders.xlab.patient.module.healthchart.bp;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/1.
 */
public interface IBloodPressurePresenter extends IBasePresenter {
    void getBPList(String patientId, boolean isRefresh);
}
