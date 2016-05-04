package com.wonders.xlab.patient.mvp.model;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/4.
 * 获取验证码Model回调与接口
 */
public interface GetCaptureModelContract {
    interface ViewListener extends BaseModelListener {
        void onGetCaptureSuccess();
    }

    interface Actions extends IBaseModel {
        void getCapture(String mobile);
    }
}
