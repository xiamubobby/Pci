package com.wonders.xlab.pci.module.record.report.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.record.report.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.report.bean.ReportDetailImageBean;
import com.wonders.xlab.pci.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.record.report.mvn.api.ReportDetailAPI;
import com.wonders.xlab.pci.mvn.entity.record.report.ReportDetailEntity;
import com.wonders.xlab.pci.module.record.report.mvn.view.ReportDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailModel extends BaseModel<ReportDetailEntity> {
    private ReportDetailView mDetailView;
    private ReportDetailAPI mReportDetailAPI;

    private int page = -1;
    private boolean isLast = false;

    public ReportDetailModel(ReportDetailView detailView) {
        mDetailView = detailView;
        mReportDetailAPI = mRetrofit.create(ReportDetailAPI.class);
    }

    public void getReportDetails(String userId) {
        if (!isLast) {
            setObservable(mReportDetailAPI.getReports(userId, page + 1));
        }
    }

    @Override
    protected void onSuccess(@NonNull ReportDetailEntity response) {
        ReportDetailEntity.RetValuesEntity entity = response.getRet_values();
        if (entity == null) {
            mDetailView.getReportsFailed("获取数据失败，请重试！");
            return;
        }

        page = entity.getNumber();
        isLast = entity.isLast();

        List<ReportDetailBean> reportDetailBeanList = new ArrayList<>();
        for (ReportDetailEntity.RetValuesEntity.ContentEntity contentEntity: entity.getContent()) {

            ReportDetailBean detailBean = new ReportDetailBean();
            detailBean.setTitle(contentEntity.getTitle());
            List<ReportDetailImageBean> detailImageBeanList = new ArrayList<>();
            for (int j = 0; j < contentEntity.getUserCases().size(); j++) {
                ReportDetailEntity.RetValuesEntity.ContentEntity.UserCasesEntity userCasesEntity = contentEntity.getUserCases().get(j);
                ReportDetailImageBean imageBean = new ReportDetailImageBean();
                imageBean.setName(userCasesEntity.getName());
                imageBean.setPosition(j);
                imageBean.setPortraitUrl(userCasesEntity.getCaseUrl());
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
