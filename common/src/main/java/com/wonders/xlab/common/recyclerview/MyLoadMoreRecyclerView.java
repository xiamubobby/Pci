package com.wonders.xlab.common.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.wonders.xlab.common.recyclerview.adapter.loadmore.RecyclerViewPositionHelper;

/**
 * Created by hua on 16/3/15.
 */
public class MyLoadMoreRecyclerView extends RecyclerView {
    private int mTotalItemCount;
    private int mVisibleItemCount;
    private int mFirstVisibleItem;
    private int lastVisibleItemPosition;
    private boolean isLoadingMore;
    private int previousTotal;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean mIsLoadMoreEnabled = false;

    private LoadMoreScrollListener loadMoreScrollListener;


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public boolean isLoadMoreEnabled() {
        return mIsLoadMoreEnabled;
    }

    public interface OnLoadMoreListener {
        void loadMore(int itemsCount, final int maxLastVisiblePosition);
    }

    public MyLoadMoreRecyclerView(Context context) {
        super(context);
    }

    public MyLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID,
        PUZZLE,
    }

    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    private RecyclerViewPositionHelper mRecyclerViewHelper;

    public void enableLoadMore() {
        mIsLoadMoreEnabled = true;
        init();
    }

    public void disableLoadMore() {
        mIsLoadMoreEnabled = false;
    }

    private void init() {
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(this);

        this.removeOnScrollListener(loadMoreScrollListener);

        loadMoreScrollListener = new LoadMoreScrollListener();
        this.addOnScrollListener(loadMoreScrollListener);
    }

    class LoadMoreScrollListener extends OnScrollListener {
        private int[] lastPositions;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManagerType == null) {
                if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                } else if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                } else {
                    throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            mTotalItemCount = layoutManager.getItemCount();
            mVisibleItemCount = layoutManager.getChildCount();

            switch (layoutManagerType) {
                case LINEAR:
                    mFirstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    lastVisibleItemPosition = mRecyclerViewHelper.findLastVisibleItemPosition();
                    break;
                case GRID:
                    if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager ly = (GridLayoutManager) layoutManager;
                        lastVisibleItemPosition = ly.findLastVisibleItemPosition();
                        mFirstVisibleItem = ly.findFirstVisibleItemPosition();
                    }
                    break;
                case STAGGERED_GRID:
                    if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager sy = (StaggeredGridLayoutManager) layoutManager;

                        if (lastPositions == null)
                            lastPositions = new int[sy.getSpanCount()];

                        sy.findLastVisibleItemPositions(lastPositions);
                        lastVisibleItemPosition = findMax(lastPositions);

                        sy.findFirstVisibleItemPositions(lastPositions);
                        mFirstVisibleItem = findMin(lastPositions);
                    }
                    break;
            }

            if (isLoadingMore) {
                //todo: there are some bugs needs to be adjusted for admob adapter
                if (mTotalItemCount > previousTotal) {
                    isLoadingMore = false;
                    previousTotal = mTotalItemCount;
                }
            }
            boolean casetest = (mTotalItemCount - mVisibleItemCount) <= mFirstVisibleItem;
            if (!isLoadingMore && casetest) {
                if (null != mOnLoadMoreListener) {
                    mOnLoadMoreListener.loadMore(getAdapter().getItemCount(), lastVisibleItemPosition);
                }
                isLoadingMore = true;
                previousTotal = mTotalItemCount;
            }

        }
    }

    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    private int findMin(int[] lastPositions) {
        int min = Integer.MAX_VALUE;
        for (int value : lastPositions) {
            if (value != RecyclerView.NO_POSITION && value < min)
                min = value;
        }
        return min;
    }
}
