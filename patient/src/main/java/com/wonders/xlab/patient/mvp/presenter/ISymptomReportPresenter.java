package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/24.
 * 健康报表-不适症状
 */
public interface ISymptomReportPresenter extends IBasePresenter {
    void getSymptomList(String patientId, long startTime, long endTime, boolean refresh);
}
