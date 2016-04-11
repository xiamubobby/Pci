package im.hua.library.base;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 15/12/14.
 */
public class BaseFragment extends Fragment {
    private List<BasePresenter> mBasePresenterList;

    private List<IBasePresenter> mIBasePresenterList;

    private ProgressDialog mDialog;

    /**
     * 发起两次toast的时间间隔的最小值，否则不显示第二次
     *
     * ms
     */
    private long mShowToastInterval = 800;

    private long mLastToastTime = 0;

    public void showShortToast(String message) {
        long nowTime = Calendar.getInstance().getTimeInMillis();
        if (nowTime - mLastToastTime < mShowToastInterval) {
            return;
        }
        mLastToastTime = nowTime;

        if (!TextUtils.isEmpty(message)) {
            if (null == getActivity()) {
                return;
            }
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showLongToast(String message) {
        long nowTime = Calendar.getInstance().getTimeInMillis();
        if (nowTime - mLastToastTime < mShowToastInterval) {
            return;
        }
        mLastToastTime = nowTime;

        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void showProgressDialog(String title, String message) {
        if (null == mDialog) {
            mDialog = new ProgressDialog(getActivity());
        }
        if (!TextUtils.isEmpty(title)) {
            mDialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            mDialog.setMessage(message);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }

    }

    public void dismissProgressDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void addPresenter(BasePresenter presenter) {
        if (mBasePresenterList == null) {
            mBasePresenterList = new ArrayList<>();
        }
        mBasePresenterList.add(presenter);
    }

    public void addPresenter(IBasePresenter presenter) {
        if (mIBasePresenterList == null) {
            mIBasePresenterList = new ArrayList<>();
        }
        if (presenter != null) {
            mIBasePresenterList.add(presenter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mBasePresenterList != null) {
            for (BasePresenter presenter : mBasePresenterList) {
                presenter.onDestroy();
                presenter = null;
            }
            mBasePresenterList.clear();
            mBasePresenterList = null;
        }
        if (null != mIBasePresenterList) {
            for (IBasePresenter presenter : mIBasePresenterList) {
                presenter.onDestroy();
                presenter = null;
            }
            mIBasePresenterList.clear();
            mIBasePresenterList = null;
        }
    }
}