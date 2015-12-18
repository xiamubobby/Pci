package com.wonders.xlab.pci.mvn.api.task;

import com.wonders.xlab.pci.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.mvn.entity.task.SymptomEntity;

import rx.Observable;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomAPI {
    Observable<SymptomEntity> getSymptoms();

    Observable<SimpleEntity> saveSymptoms();
}
