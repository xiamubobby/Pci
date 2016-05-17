package im.hua.library.base.mvp.impl;

/**
 * Created by hua on 16/2/22.
 */
public class BasePagePresenter extends BasePresenter {
    /**
     * 分页
     * 1、{@link #resetPageInfo()} when refresh
     * 2、{@link #getNextPageIndex()} when you want to get the next page data
     * 3、{@link #updatePageInfo(int, boolean, boolean)} when get response from the server
     * 4、{@link #shouldAppend()} when you want to show the list view or append the data to your list view
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

    /**
     * 是否没有更多数据
     * 即是否不可以继续加载更多
     * @return
     */
    protected boolean noMoreData() {
        return mIsLast;
    }

    protected boolean shouldAppend() {
        return mCurrentIndex > 0;
    }
}
