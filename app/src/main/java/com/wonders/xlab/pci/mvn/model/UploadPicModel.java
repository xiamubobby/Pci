package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.wonders.xlab.pci.mvn.api.UploadPicAPI;
import com.wonders.xlab.pci.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.mvn.view.UploadPicView;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by hua on 15/12/17.
 */
public class UploadPicModel extends BaseModel<SimpleEntity> {
    private UploadPicView mUploadPicView;
    private UploadPicAPI mUploadPicAPI;

    public UploadPicModel(UploadPicView uploadPicView) {
        mUploadPicView = uploadPicView;
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
        setObservable(mUploadPicAPI.upload(iUserId, identityHashMap));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUploadPicView.uploading();
    }

    @Override
    protected void onSuccess(@NonNull SimpleEntity response) {
        mUploadPicView.uploadPicsSuccess();
    }

    @Override
    protected void onFailed(String message) {
        mUploadPicView.uploadPicsFailed(message);
    }
}
