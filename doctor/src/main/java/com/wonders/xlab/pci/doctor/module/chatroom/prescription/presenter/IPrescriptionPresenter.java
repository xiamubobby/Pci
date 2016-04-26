package com.wonders.xlab.pci.doctor.module.chatroom.prescription.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/26.
 */
public interface IPrescriptionPresenter extends IBasePresenter {
    void getPrescriptionList(String patientId, String doctorId);
}
