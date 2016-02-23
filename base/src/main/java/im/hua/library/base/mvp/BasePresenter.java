package im.hua.library.base.mvp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/2/22.
 */
public class BasePresenter {
    private List<BaseModel> mBaseModelList;

    protected void addModel(BaseModel model) {
        if (mBaseModelList == null) {
            mBaseModelList = new ArrayList<>();
        }
        if (model != null) {
            mBaseModelList.add(model);
        }
    }

    public void onDestroy() {
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
    }
}