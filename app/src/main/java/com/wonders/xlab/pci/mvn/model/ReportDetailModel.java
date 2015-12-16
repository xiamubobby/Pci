package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.bean.ReportDetailImageBean;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.ReportDetailAPI;
import com.wonders.xlab.pci.mvn.entity.report.ReportDetailEntity;
import com.wonders.xlab.pci.mvn.view.ReportDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailModel extends BaseModel<ReportDetailEntity> {
    private ReportDetailView mDetailView;
    private ReportDetailAPI mReportDetailAPI;

    public ReportDetailModel(ReportDetailView detailView) {
        mDetailView = detailView;
        mReportDetailAPI = mRetrofit.create(ReportDetailAPI.class);
    }

    public void getReportDetails() {
//        setObservable(mReportDetailAPI.getReports());
        onSuccess(null);
    }

    @Override
    protected void onSuccess(@NonNull ReportDetailEntity response) {
        List<ReportDetailBean> reportDetailBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReportDetailBean detailBean = new ReportDetailBean();
            detailBean.setTitle("病历单" + i);
            List<ReportDetailImageBean> detailImageBeanList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                ReportDetailImageBean imageBean = new ReportDetailImageBean();
                imageBean.setName("image" + i + ":" + j);
                imageBean.setPosition(j);
                imageBean.setPortraitUrl(Constant.TEST_PORTRAIT);
                detailImageBeanList.add(imageBean);
            }
            detailBean.setPicUrlList(detailImageBeanList);
            reportDetailBeanList.add(detailBean);
        }
        mDetailView.showReportList(reportDetailBeanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
