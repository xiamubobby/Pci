package com.wonders.xlab.patient.mvp.model;

import java.io.File;
import java.util.List;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/3/17.
 */
public interface IUploadModel extends IBaseModel {
    void upload(String userId, List<File> fileList);
}
