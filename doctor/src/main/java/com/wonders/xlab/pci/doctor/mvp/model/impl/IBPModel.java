package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/2/22.
 */
public interface IBPModel extends IBaseModel {
    void onReceiveBPSuccess(BPEntity bpEntity);
}
