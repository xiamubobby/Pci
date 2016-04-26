package com.wonders.xlab.patient.mvp.model.impl;

import android.support.annotation.NonNull;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.data.api.UploadPicAPI;
import com.wonders.xlab.patient.mvp.model.IUploadModel;

import java.io.File;
import java.util.List;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public void upload(String userId, String title, List<File> fileList) {

        if (fileList != null && fileList.size() > 0) {
            RequestBody iUserId = RequestBody.create(MediaType.parse("text"), userId);
            RequestBody iTitle = RequestBody.create(MediaType.parse("text"), title);

//            IdentityHashMap<String, MultipartBody> identityHashMap = new IdentityHashMap<>();

            MultipartBody.Builder builder = new MultipartBody.Builder();

            for (File itemFile : fileList) {
                if (itemFile != null && itemFile.exists()) {
//                    MultipartBody.Part part = MultipartBody.Part.create(RequestBody.create(MediaType.parse("image/*"), itemFile));
                    builder.addFormDataPart("file",itemFile.getName(),RequestBody.create(MediaType.parse("image/*"), itemFile));

//                    MultipartBody multipartBody = new MultipartBody.Builder().addFormDataPart("file", itemFile.getName(), RequestBody.create(MediaType.parse("image/*"), itemFile)).build();
//                    identityHashMap.put(new String("file"), multipartBody);
                }
            }

            request(mUploadPicAPI.upload(iUserId, iTitle, builder.build()), true);
//            request(mUploadPicAPI.upload(iUserId, identityHashMap), true);
        } else {
            onFailed(-1, "请选择要上传的图片");
        }
    }


    @Override
    protected void onSuccess(@NonNull SimpleEntity response) {
        mUploadPicModelListener.uploadPicsSuccess();
    }

    @Override
    protected void onFailed(int code, String message) {
        mUploadPicModelListener.onReceiveFailed(code, message);
    }


    public interface UploadPicModelListener extends BaseModelListener {
        void uploadPicsSuccess();
    }
}
