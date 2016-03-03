package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.symptom.bean.SymptomBean;

import java.util.List;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/2/24.
 */
public interface ISymptomPresenter extends IBasePresenter {
    void showSymptomList(List<SymptomBean> symptomBeanList);

    void saveCommentSuccess();
}
