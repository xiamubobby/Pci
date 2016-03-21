package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/21.
 */
public interface IBPReportPresenter extends IBasePresenter {
    /**
     * 查询属于当前患者id的今天的血压数据
     * @param patientId
     */
    void getBPCacheList(String patientId);
}
