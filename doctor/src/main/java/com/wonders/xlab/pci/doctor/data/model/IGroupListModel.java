package com.wonders.xlab.pci.doctor.data.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/11.
 */
public interface IGroupListModel extends IBaseModel {
    void getGroupList(String doctorId, int page, int size);
}
