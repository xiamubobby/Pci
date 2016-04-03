package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/1.
 */
public interface IBloodSugarModel extends IBaseModel {
    void getBSList(String userId, int page, int pageSize);
}
