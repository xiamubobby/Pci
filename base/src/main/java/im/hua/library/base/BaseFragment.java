package im.hua.library.base;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BaseModel;

/**
 * Created by hua on 15/12/14.
 */
public class BaseFragment extends Fragment {
    private List<BaseModel> mBaseModelList;

    public void addModel(BaseModel model) {
        if (mBaseModelList == null) {
            mBaseModelList = new ArrayList<>();
        }
        mBaseModelList.add(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBaseModelList != null) {
            for (BaseModel model : mBaseModelList) {
                model.cancel();
            }
            mBaseModelList.clear();
            mBaseModelList = null;
        }
    }
}