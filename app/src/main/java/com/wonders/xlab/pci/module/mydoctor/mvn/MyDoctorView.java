package com.wonders.xlab.pci.module.mydoctor.mvn;

import com.wonders.xlab.pci.module.base.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 16/1/28.
 */
public interface MyDoctorView extends BaseView {
    void showDoctorList(List<DoctorInfoEntity> doctorInfoList);

    void showError(String message);
}
