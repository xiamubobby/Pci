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

    protected void updatePageInfo(int currentIndex) {
        mCurrentIndex = currentIndex;
    }

    protected void resetPageInfo() {
        mCurrentIndex = -1;
    }

    protected int getNextPageIndex() {
        int next = mCurrentIndex + 1;
        return next < 0 ? 0 : next;
    }

    protected boolean shouldAppend() {
        return mCurrentIndex > 0;
    }
}
