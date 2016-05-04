package com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by jimmy on 16/5/3.
 */
public interface ISurgicalHistoryPresenter extends IBasePresenter {

    void getSurgicalHistoryList(String patientId, String doctorId);
}
