package com.wonders.xlab.pci.module.base.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.module.base.mvn.api.UploadPicAPI;
import com.wonders.xlab.pci.module.base.mvn.entity.SimpleEntity;
import com.wonders.xlab.pci.module.base.mvn.view.UploadPicView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

        if (fileList != null && fileList.size() > 0) {
            RequestBody iUserId = RequestBody.create(MediaType.parse("text"), userId);

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

            setObservable(mUploadPicAPI.upload(iUserId, builder.build()));
//            fetchData(mUploadPicAPI.upload(iUserId, identityHashMap), true);
        } else {
            onFailed("请选择要上传的图片");
        }
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
