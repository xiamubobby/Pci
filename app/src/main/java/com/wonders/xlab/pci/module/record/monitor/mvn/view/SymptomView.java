package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.mvn.entity.record.monitor.SymptomEntity;
import com.wonders.xlab.pci.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public interface SymptomView extends BaseView {
    void onFailed(String message);

    void showSymptomLabels(List<String> symptomList);

    void showSymptomAdvices(List<SymptomEntity.RetValuesEntity.UserSymptomAdvicesEntity> symptomAdviceList);
}
