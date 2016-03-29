package com.wonders.xlab.pci.doctor.mvp.presenter.listener;

import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomBean;

import java.util.List;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomPresenterListener extends BasePresenterListener {
    void showSymptomList(List<SymptomBean> symptomBeanList);

    void saveCommentSuccess();
}
