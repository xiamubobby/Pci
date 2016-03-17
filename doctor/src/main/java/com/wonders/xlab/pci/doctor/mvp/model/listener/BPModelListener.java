package com.wonders.xlab.pci.doctor.mvp.model.listener;

import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/22.
 */
public interface BPModelListener extends BaseModelListener {
    void onReceiveBPSuccess(BPEntity bpEntity);
}
