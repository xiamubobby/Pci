package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.model.IUploadModel;
import com.wonders.xlab.patient.mvp.model.impl.UploadPicModel;
import com.wonders.xlab.patient.mvp.presenter.IUploadPicPresenter;

import java.io.File;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/17.
 */
public class UploadPicPresenter extends BasePresenter implements IUploadPicPresenter, UploadPicModel.UploadPicModelListener {

    private UploadPicModelListener mUploadPicModelListener;
    private IUploadModel mUploadModel;

    public UploadPicPresenter(UploadPicModelListener uploadPicModelListener) {
        mUploadPicModelListener = uploadPicModelListener;
        mUploadModel = new UploadPicModel(this);
        addModel(mUploadModel);
    }

    @Override
    public void upload(String patientId, String title, List<File> fileList) {
        mUploadPicModelListener.uploading();
        mUploadModel.upload(patientId, title, fileList);
    }

    @Override
    public void uploadPicsSuccess() {
        mUploadPicModelListener.hideLoading();
        mUploadPicModelListener.uploadPicsSuccess("上传成功！我们将在整理好资料后同步给您。请耐心等待。");
    }

    @Override
    public void onReceiveFailed(String message) {
        mUploadPicModelListener.hideLoading();
        mUploadPicModelListener.showError(message);

    }

    public interface UploadPicModelListener extends BasePresenterListener {
        void uploading();

        void uploadPicsSuccess(String message);
    }
}
