package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

/**
 * Created by hua on 16/2/22.
 */
public interface IBloodPressureModel {
    void onReceiveBPSuccess(BPEntity bpEntity);

    void onReceiveBPFailed(Throwable throwable);
}
