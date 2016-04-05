package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/24.
 */
public interface ISymptomRetrieveModel extends IBaseModel {
    void getSymptomList(String patientId, long startTime, long endTime, int page, int size);
}
