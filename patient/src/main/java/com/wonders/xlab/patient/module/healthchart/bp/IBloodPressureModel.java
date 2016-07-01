package com.wonders.xlab.patient.module.healthchart.bp;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/1.
 */
public interface IBloodPressureModel extends IBaseModel {
    void getBPList(String patientId, long startTime, long endTime, int page, int size);
}
