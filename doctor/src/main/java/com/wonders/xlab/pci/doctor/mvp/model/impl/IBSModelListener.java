package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/22.
 */
public interface IBSModelListener extends BaseModelListener {
    void onReceiveBSSuccess(BSEntity bsEntity);
}
