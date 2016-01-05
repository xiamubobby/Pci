package com.wonders.xlab.pci.module.base.mvn.view;

/**
 * Created by hua on 15/12/18.
 */
public interface MeasureResultView {
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
