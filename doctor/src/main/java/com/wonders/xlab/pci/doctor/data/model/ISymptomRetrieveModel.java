package com.wonders.xlab.pci.doctor.data.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/24.
 */
public interface ISymptomRetrieveModel extends IBaseModel {
    void getSymptomList(String patientId, int page, int size);
}