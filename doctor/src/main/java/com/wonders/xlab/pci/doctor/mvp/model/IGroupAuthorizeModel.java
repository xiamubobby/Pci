package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupAuthorizeBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/14.
 */
public interface IGroupAuthorizeModel extends IBaseModel {
    void authorize(String doctorId,String doctorGroupId,GroupAuthorizeBody body);
}
