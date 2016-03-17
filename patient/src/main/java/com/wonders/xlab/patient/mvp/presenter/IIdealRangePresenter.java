package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 * 健康监测数据理想范围
 */
public interface IIdealRangePresenter extends IBasePresenter {
    /**
     * 获取血压理想范围
     *
     * @param userId
     */
    void fetchIdealBPRange(String userId);

    /**
     * 获取血糖理想范围
     *
     * @param userId
     */
    void fetchIdealBSRange(String userId);
}
