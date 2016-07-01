package com.wonders.xlab.patient.module.dailyreport.datarecord.symptom;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface ISymptomPresenter extends IBasePresenter {
    /**
     * 获取全部不适症状
     */
    void getSymptoms();

    /**
     * 保存不适症状
     *
     * @param patientId
     * @param symptomIdsStr
     */
    void saveSymptom(String patientId, String[] symptomIdsStr);
}
