package com.wonders.xlab.patient.mvp.model.impl;

import android.support.annotation.NonNull;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.UploadPicAPI;
import com.wonders.xlab.patient.mvp.model.IUploadModel;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.List;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/17.
 */
public class UploadPicModel extends PatientBaseModel<SimpleEntity> implements IUploadModel {
    private UploadPicModelListener mUploadPicModelListener;
    private UploadPicAPI mUploadPicAPI;

    public UploadPicModel(UploadPicModelListener uploadPicModelListener) {
        mUploadPicModelListener = uploadPicModelListener;
        mUploadPicAPI = mRetrofit.create(UploadPicAPI.class);
    }

    public void upload(String userId, List<File> fileList) {
        IdentityHashMap<String, RequestBody> identityHashMap = new IdentityHashMap<>();

        RequestBody iUserId = RequestBody.create(MediaType.parse("text"), userId);

        if (fileList != null && fileList.size() > 0) {

            for (int i = 0; i < fileList.size(); i++) {

                File itemFile = fileList.get(i);

                if (itemFile != null && itemFile.exists()) {

                    MultipartBuilder multipartBuilder = new MultipartBuilder();

                    multipartBuilder.addFormDataPart("file", itemFile.getName(), RequestBody.create(MediaType.parse("image/*"), itemFile));

                    identityHashMap.put(new String("file"), multipartBuilder.build());
                }
            }
        }
        fetchData(mUploadPicAPI.upload(iUserId, identityHashMap), true);
    }


    @Override
    protected void onSuccess(@NonNull SimpleEntity response) {
        mUploadPicModelListener.uploadPicsSuccess();
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mUploadPicModelListener.onReceiveFailed(e.getMessage());
    }


    public interface UploadPicModelListener extends BaseModelListener {
        void uploadPicsSuccess();
    }
}
