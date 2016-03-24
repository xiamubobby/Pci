package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IBPSavePresenter extends IBasePresenter {

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    void saveBP(String userId, BPEntityList bpEntityList);

    /**
     * 保存一条血压数据
     *
     * @param userId
     * @param date
     * @param heartRate
     * @param systolicPressure
     * @param diastolicPressure
     */
    void saveBPSingle(String userId, long date, int heartRate, int systolicPressure, int diastolicPressure);
}
