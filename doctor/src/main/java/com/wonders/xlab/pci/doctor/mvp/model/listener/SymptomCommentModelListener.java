package com.wonders.xlab.pci.doctor.mvp.model.listener;

import com.wonders.xlab.pci.doctor.mvp.entity.SymptomCommentEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/24.
 */
public interface SymptomCommentModelListener extends BaseModelListener {
    void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity);
}
