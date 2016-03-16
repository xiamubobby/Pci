package com.wonders.xlab.patient.mvp.presenter.impl;

/**
 * Created by hua on 15/12/18.
 */
public interface IMeasureResultPresenter {
    /**
     * 保存成功，有效数据
     */
    void svSuccess();

    /**
     * 重复数据
     */
    void svDuplicate();

    void svFailed(String message);

    void svShowLoading();

    void svHideLoading();
}
