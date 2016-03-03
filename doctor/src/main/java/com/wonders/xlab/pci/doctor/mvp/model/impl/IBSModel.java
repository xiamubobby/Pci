package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/2/22.
 */
public interface IBSModel extends IBaseModel {
    void onReceiveBSSuccess(BSEntity bsEntity);
}
