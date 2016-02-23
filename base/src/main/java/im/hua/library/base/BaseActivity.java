package im.hua.library.base;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 15/12/14.
 */
public class BaseActivity extends AppCompatActivity {

    private List<BasePresenter> mBasePresenterList;

    public void addPresenter(BasePresenter presenter) {
        if (mBasePresenterList == null) {
            mBasePresenterList = new ArrayList<>();
        }
        if (presenter != null) {
            mBasePresenterList.add(presenter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasePresenterList != null) {
            for (BasePresenter presenter : mBasePresenterList) {
                presenter.onDestroy();
            }
            mBasePresenterList.clear();
            mBasePresenterList = null;
        }
    }
}
