package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/24.
 */
public interface ISymptomSaveModel extends IBaseModel {
    void saveSymptom(String userId, String[] symptomIdsStr);
}
