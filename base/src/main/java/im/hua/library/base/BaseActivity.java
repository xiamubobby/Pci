package im.hua.library.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 15/12/14.
 */
public class BaseActivity extends AppCompatActivity {

    private List<BasePresenter> mBasePresenterList;

    private List<IBasePresenter> mIBasePresenterList;

    private ProgressDialog mDialog;

    private Toast mShortToast;
    private Toast mLongToast;

    /**
     * 发起两次toast的时间间隔的最小值，否则不显示第二次
     * <p/>
     * ms
     */
    private long mShowToastInterval = 800;

    private long mLastToastTime = 0;

    public void showShortToast(String message) {
        if (null != mShortToast) {
            mShortToast.cancel();
        }
        if (null != mLongToast) {
            mLongToast.cancel();
        }

        if (!TextUtils.isEmpty(message) && !"null".equals(message)) {
            mShortToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mShortToast.show();
        }
    }

    public void showLongToast(String message) {
        if (null != mShortToast) {
            mShortToast.cancel();
        }
        if (null != mLongToast) {
            mLongToast.cancel();
        }

        if (!TextUtils.isEmpty(message) && !"null".equals(message)) {
            mLongToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            mLongToast.show();
        }
    }

    public interface OnDialogDismissListener {
        void onDismiss();
    }

    public void showProgressDialog(String title, String message, final OnDialogDismissListener listener) {
        if (null == mDialog) {
            mDialog = new ProgressDialog(this);
        }
        if (null != listener) {
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    listener.onDismiss();
                }
            });
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
        if (presenter != null) {
            mBasePresenterList.add(presenter);
        }
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
    public void onDestroy() {
        super.onDestroy();
        if (null != mShortToast) {
            mShortToast.cancel();
        }
        if (null != mLongToast) {
            mLongToast.cancel();
        }
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

    public void setRefreshing(final SwipeRefreshLayout swipeRefreshLayout, final boolean refreshing) {
        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(refreshing);
                }
            });
        }
    }
}
