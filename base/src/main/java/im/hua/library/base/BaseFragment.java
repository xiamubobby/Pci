package im.hua.library.base;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 15/12/14.
 */
public class BaseFragment extends Fragment {
    private List<BasePresenter> mBasePresenterList;

    public void addPresenter(BasePresenter presenter) {
        if (mBasePresenterList == null) {
            mBasePresenterList = new ArrayList<>();
        }
        mBasePresenterList.add(presenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBasePresenterList != null) {
            for (BasePresenter presenter : mBasePresenterList) {
                presenter.onDestroy();
            }
            mBasePresenterList.clear();
            mBasePresenterList = null;
        }
    }
}