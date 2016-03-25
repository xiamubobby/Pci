package im.hua.library.base.mvp.impl;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class BasePresenter implements IBasePresenter {

    private List<BaseModel> mBaseModelList;
    private List<IBaseModel> mIBaseModelList;

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
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
        if (mIBaseModelList != null) {
            for (IBaseModel model : mIBaseModelList) {
                model.cancel();
            }
            mIBaseModelList.clear();
            mIBaseModelList = null;
        }
    }
}
