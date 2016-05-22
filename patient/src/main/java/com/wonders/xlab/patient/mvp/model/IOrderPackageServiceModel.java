package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/21.
 */
public interface IOrderPackageServiceModel extends IBaseModel {
    void orderPackage(String patientId, String packageId, String paymentChannel);
}
