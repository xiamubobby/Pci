package com.wonders.xlab.pci.doctor.data.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/5/4.
 */
public interface IMedicalRecordsModel extends IBaseModel{
    void getMedicalRecordsList(String patientId, int pageIndex);
}
