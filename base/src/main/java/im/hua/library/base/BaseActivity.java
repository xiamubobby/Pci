package im.hua.library.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 15/12/14.
 */
public class BaseActivity extends AppCompatActivity {

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
        Log.d("BaseActivity", "nowTime:" + nowTime);
        if (nowTime - mLastToastTime < mShowToastInterval) {
            return;
        }
        mLastToastTime = nowTime;

        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showLongToast(String message) {
        long nowTime = Calendar.getInstance().getTimeInMillis();
        if (nowTime - mLastToastTime < mShowToastInterval) {
            return;
        }
        mLastToastTime = nowTime;

        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void showProgressDialog(String title, String message) {
        if (null == mDialog) {
            mDialog = new ProgressDialog(this);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (mBasePresenterList != null) {
            for (BasePresenter presenter : mBasePresenterList) {
                presenter.onDestroy();
            }
            mBasePresenterList.clear();
            mBasePresenterList = null;
        }
        if (null != mIBasePresenterList) {
            for (IBasePresenter presenter : mIBasePresenterList) {
                presenter.onDestroy();
            }
            mIBasePresenterList.clear();
            mIBasePresenterList = null;
        }
    }
}
