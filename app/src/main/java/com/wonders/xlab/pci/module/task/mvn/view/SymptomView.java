package com.wonders.xlab.pci.module.task.mvn.view;

import com.wonders.xlab.pci.module.base.mvn.entity.task.SymptomEntity;
import com.wonders.xlab.pci.module.base.mvn.view.BaseView;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomView extends BaseView {
    void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

    void getSymptomsFailed(String message);

    void saveSuccess();

    void saveFailed(String message);
}
