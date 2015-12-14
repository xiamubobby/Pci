package com.wonders.xlab.common.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hua on 15/6/21.
 * 自定义的加入监听到达底部的LinearLayoutManager or GridLayoutManager
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {

        void loadMoreToBottom();

        void loadMoreToTop();
    }

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;

    private boolean isLoadingMore = false;

    private boolean canLoadMore = true;

    private boolean isFirstScroll = true;

    private final Object loadObject = new Object();

    private LayoutManager layoutManager;

    private boolean isReverse;

    private boolean isScrollingToTop;

    private int childCounts;//current show counts
    private int totalItemCount;//total counts

    private void init() {

        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (loadObject) {
                    if (childCounts > 0 && newState == SCROLL_STATE_IDLE && !isLoadingMore && onLoadMoreListener != null && canLoadMore) {//
                        isLoadingMore = true;
                        if ((lastVisibleItemPosition >= totalItemCount - 1)) {
                            if (!isScrollingToTop) {
                                onLoadMoreListener.loadMoreToBottom();
                            } else {
                                onLoadMoreListener.loadMoreToTop();
                            }
                        }
                        if (firstVisibleItemPosition <= 0) {
                            if (isScrollingToTop) {
                                onLoadMoreListener.loadMoreToTop();
                            } else {
                                onLoadMoreListener.loadMoreToBottom();
                            }
                        }
                        isLoadingMore = false;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirstScroll) {
                    layoutManager = getLayoutManager();
                }
                childCounts = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                int total = layoutManager.getItemCount();

                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

                    isReverse = linearLayoutManager.getReverseLayout();

                    lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    firstVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition == -1) {
                        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    }
                    if (firstVisibleItemPosition == -1) {
                        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    }
                } else if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    isReverse = gridLayoutManager.getReverseLayout();
                    lastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    firstVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition == -1) {
                        lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    }
                    if (firstVisibleItemPosition == -1) {
                        firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager must be LinearLayoutManager or GridLayoutManager");
                }

                if (isReverse) {
                    if (dy >= 0) {
                        isScrollingToTop = true;
                    } else {
                        isScrollingToTop = false;
                    }
                } else {
                    if (dy <= 0) {
                        isScrollingToTop = true;
                    } else {
                        isScrollingToTop = false;
                    }
                }
                if (isFirstScroll) {
                    isFirstScroll = false;
                    if (lastVisibleItemPosition >= total) {//if all data can be show in the screen, then don't trigger the listener
                        canLoadMore = false;
                    } else {
                        canLoadMore = true;
                    }
                }
            }
        });

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}
