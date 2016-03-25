package im.hua.library.base.mvp.impl;

/**
 * Created by hua on 16/2/22.
 */
public class BasePagePresenter extends BasePresenter {
    /**
     * 分页
     */
    protected int DEFAULT_PAGE_SIZE = 20;
    protected int mCurrentIndex = -1;
    protected boolean mIsFirst = true;
    protected boolean mIsLast = false;

    protected void updatePageInfo(int currentIndex) {
        mCurrentIndex = currentIndex;
    }

    protected void updatePageInfo(int currentIndex, boolean isFirst, boolean isLast) {
        mCurrentIndex = currentIndex;
        mIsFirst = isFirst;
        mIsLast = isLast;
    }

    protected void resetPageInfo() {
        mCurrentIndex = -1;
        mIsFirst = true;
        mIsLast = false;
    }

    protected int getNextPageIndex() {
        int next = mCurrentIndex + 1;
        return next < 0 ? 0 : next;
    }

    protected boolean shouldAppend() {
        return mCurrentIndex > 0;
    }
}
