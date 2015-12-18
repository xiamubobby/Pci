package com.wonders.xlab.pci.mvn.view.task;

import com.wonders.xlab.pci.mvn.BaseView;
import com.wonders.xlab.pci.mvn.entity.task.SymptomEntity;

import java.util.List;

/**
 * Created by hua on 15/12/18.
 */
public interface SymptomView extends BaseView {
    void showSymptoms(List<SymptomEntity.RetValuesEntity> entityList);

    void getSymptomsFailed(String message);

    void saveSuccess();

    void saveFailed(String message);
}
