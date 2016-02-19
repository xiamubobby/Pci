package im.hua.library.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


/**
 * Created by hua on 15/12/1.
 * 在布局中include  layout/appbar即可
 */
public abstract class AppbarActivity extends BaseActivity {
    private Toolbar mToolbar;

    private OnNavigationClickListener mOnNavigationClickListener;

    public abstract int getContentLayout();

    public abstract String getToolbarTitle();

    public abstract Toolbar getToolbar();

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
        mToolbar = getToolbar();
        if (mToolbar != null) {
            mToolbar.setTitle(getToolbarTitle());
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
}
