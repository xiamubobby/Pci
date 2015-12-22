package com.wonders.xlab.pci.module.record.monitor.mvn.view;

import com.wonders.xlab.pci.mvn.view.BaseView;

/**
 * Created by hua on 15/12/21.
 */
public interface BPView extends BaseView {
    void showCurrentBPPieChart();

    void showHistoryBPLineChart();

    void onFailed(String message);
}
