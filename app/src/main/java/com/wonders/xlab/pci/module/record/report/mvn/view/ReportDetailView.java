package com.wonders.xlab.pci.module.record.report.mvn.view;

import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.mvn.view.BaseView;

import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public interface ReportDetailView extends BaseView {
    void showReportList(List<ReportDetailBean> detailBeanList);
    void appendReportList(List<ReportDetailBean> detailBeanList);

    void getReportsFailed(String message);
}
