package com.wonders.xlab.patient.mvp.presenter;

import java.io.File;
import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IUploadPicPresenter extends IBasePresenter {
    void upload(String patientId, String title, List<File> fileList);
}
