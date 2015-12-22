package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.mvn.BaseView;

/**
 * Created by hua on 15/12/22.
 */
public interface SymptomView extends BaseView {
    void onFailed(String message);

    void showSymptomLabels();

    void showSymptomAdvices();
}
