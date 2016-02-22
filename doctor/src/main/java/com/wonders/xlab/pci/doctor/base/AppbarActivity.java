package com.wonders.xlab.pci.doctor.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import im.hua.library.base.BaseActivity;
import xlab.wonders.com.common.R;

/**
 * Created by hua on 15/12/1.
 * 在布局中include  layout/appbar即可
 */
public abstract class AppbarActivity extends BaseActivity {
    public View mContentView;

    private Toolbar mToolbar;

    private OnNavigationClickListener mOnNavigationClickListener;

    public abstract int getContentLayout();

    public abstract String getToolbarTitle();

    public void setToolbarTitle(String title) {
        if (mToolbar != null && !TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        }
    }

    public void setOnNavigationClickListener(OnNavigationClickListener onNavigationClickListener) {
        mOnNavigationClickListener = onNavigationClickListener;
    }

    public interface OnNavigationClickListener {
        /**
         * @return true:do not finish the activity    false:finish the activity
         */
        boolean onNavigationClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        mContentView = findViewById(android.R.id.content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getToolbarTitle());
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNavigationClickListener != null && mOnNavigationClickListener.onNavigationClick()) {
                        return;
                    }
                    finish();
                }
            });
        } else {
            Log.w("ToolbarActivity", "if you want the ToolbarActivity to manager your toolbar, you should set your toolbar's id to toolbar");
        }

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
