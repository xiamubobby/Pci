package im.hua.library.base.mvp.impl;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public class BasePresenter implements IBasePresenter {

    private List<BaseModel> mBaseModelList;
    private List<IBaseModel> mIBaseModelList;
    private boolean mIsCanceled;

    protected void addModel(BaseModel model) {
        if (mBaseModelList == null) {
            mBaseModelList = new ArrayList<>();
        }
        if (model != null) {
            mBaseModelList.add(model);
        }
    }

    protected void addModel(IBaseModel model) {
        if (mIBaseModelList == null) {
            mIBaseModelList = new ArrayList<>();
        }
        if (model != null) {
            mIBaseModelList.add(model);
        }
    }

    @Override
    public void onDestroy() {
        mIsCanceled = true;
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
                model = null;
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
        if (mIBaseModelList != null) {
            for (IBaseModel model : mIBaseModelList) {
                model.cancel();
                model = null;
            }
            mIBaseModelList.clear();
            mIBaseModelList = null;
        }
    }

    public boolean isCanceled() {
        return mIsCanceled;
    }

    protected void showError(BasePresenterListener listener, int code, String message) {
        if (null == listener) {
            return;
        }
        listener.hideLoading();
        if (code == BaseModel.ERROR_CODE_CONNECT_EXCEPTION || code == BaseModel.ERROR_CODE_CLIENT_EXCEPTION) {
            listener.showNetworkError(message);
        } else if (code == -1) {
            listener.showErrorToast(TextUtils.isEmpty(message) ? "请求出错，请重试！" : message);
        } else {
            listener.showServerError(message);
        }
    }
}
