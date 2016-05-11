package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import java.io.File;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.impl.BaseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hua on 16/5/5.
 * 市民云认证
 */
public class AuthorizeModel extends PatientBaseModel implements AuthorizeModelContract.Actions {

    private AuthAPI mAuthAPI;

    @Inject
    public AuthorizeModel(AuthAPI authAPI) {
        mAuthAPI = authAPI;
    }

    @Override
    public void authorize(String patientId, String name, String idNo, File idPic, final AuthorizeModelContract.Callback callback) {
        RequestBody patientIdRB = RequestBody.create(MediaType.parse("text"), patientId);
        RequestBody nameRB = RequestBody.create(MediaType.parse("text"), name);
        RequestBody idNoRB = RequestBody.create(MediaType.parse("text"), idNo);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (idPic != null && idPic.exists()) {
            builder.addFormDataPart("file", idPic.getName(), RequestBody.create(MediaType.parse("image/*"), idPic));
        } else {
            callback.onReceiveFailed(BaseModel.ERROR_CODE_CLIENT_EXCEPTION, "请先选择照片");
            return;
        }

        request(mAuthAPI.authorize(patientIdRB, nameRB, idNoRB, builder.build()), new Callback<SimpleEntity>() {
            @Override
            public void onSuccess(SimpleEntity response) {
                callback.authorizeSuccess(response.getMessage());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
