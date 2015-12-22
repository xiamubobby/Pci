package com.wonders.xlab.pci.mvn.view.report;

import com.wonders.xlab.pci.mvn.BaseView;

/**
 * Created by hua on 15/12/21.
 */
public interface BPView extends BaseView {


    void showHistoryBPLineChart();

    void onFailed(String message);
}
