package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;

import im.hua.library.base.mvp.impl.IBaseModel;

/**
 * Created by hua on 16/2/24.
 */
public interface ISymptomCommentModel extends IBaseModel {
    void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity);
}
