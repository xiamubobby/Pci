package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/17.
 */
public interface IDoctorAllModel extends IBaseModel{
    void getAllDoctorList(String patientId);
}
