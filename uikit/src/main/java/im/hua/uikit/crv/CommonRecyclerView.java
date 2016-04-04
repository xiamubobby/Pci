package im.hua.uikit.crv;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import im.hua.uikit.R;

/**
 * Created by hua on 16/3/24.
 */
public class CommonRecyclerView extends FrameLayout {
    private OnLoadMoreListener mOnLoadMoreListener;

    private final int LAYOUT_MANGER_LINEAR = 0;
    private final int LAYOUT_MANGER_GRID = 1;
    private final int LAYOUT_MANGER_STAGGERED_GRID = 2;

    private View mLoadingView;
    private View mEmptyView;
    private View mNetworkErrorView;
    private View mServerErrorView;

    private int layoutManager;
    private boolean reverseLayout;
    private int spanCount;
    private int orientation;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshView;

    private boolean mIsLoadMore;

    public CommonRecyclerView(Context context) {
        this(context, null);
    }

    public CommonRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonRecyclerView);

        mEmptyView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_emptyView, R.layout.crv_empty), null, false);
        mLoadingView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_loadingView, R.layout.crv_loading), null, false);
        mNetworkErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_networkErrorView, R.layout.crv_network_error), null, false);
        mServerErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_serverErrorView, R.layout.crv_server_error), null, false);
        layoutManager = array.getInteger(R.styleable.CommonRecyclerView_crvLayoutManager, LAYOUT_MANGER_LINEAR);
        spanCount = array.getInteger(R.styleable.CommonRecyclerView_crvSpanCount, 1);
        orientation = array.getInteger(R.styleable.CommonRecyclerView_orientation, RecyclerView.VERTICAL);
        reverseLayout = array.getBoolean(R.styleable.CommonRecyclerView_crvReverseLayout, false);

        array.recycle();

        mRefreshView = (SwipeRefreshLayout) LayoutInflater.from(context).inflate(R.layout.crv_recycler_view, null, false);
        mRecyclerView = (RecyclerView) mRefreshView.findViewById(R.id.recycler_view_crv);

        if (null != mRecyclerView) {
            /**
             * load more listener
             */
            mRecyclerView.addOnScrollListener(new RecyclerViewScrollListener(this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            switch (layoutManager) {
                case LAYOUT_MANGER_LINEAR:
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(context, orientation, reverseLayout));
                    break;
                case LAYOUT_MANGER_GRID:
                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, spanCount, orientation, reverseLayout));
                    break;
                case LAYOUT_MANGER_STAGGERED_GRID:
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
                    break;
            }
        }

        if (null != mLoadingView) {
            this.addView(mLoadingView);
            mLoadingView.setVisibility(GONE);
        }
        if (null != mEmptyView) {
            this.addView(mEmptyView);
            mEmptyView.setVisibility(GONE);
        }
        if (null != mNetworkErrorView) {
            this.addView(mNetworkErrorView);
            mNetworkErrorView.setVisibility(GONE);
        }
        if (null != mServerErrorView) {
            this.addView(mServerErrorView);
            mServerErrorView.setVisibility(GONE);
        }
        if (null != mRefreshView) {
            this.addView(mRefreshView);
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        if (null != mRecyclerView) {
            mRecyclerView.addItemDecoration(decor);
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        if (null != mRecyclerView) {
            mRecyclerView.setItemAnimator(animator);
        }
    }

    public void setAdapter(@NonNull final RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if (adapter.getItemCount() > 0) {
            showContentView();
        }
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getItemCount() > 0) {
                    showContentView();
                }
            }
        });
    }

    public void showNetworkErrorView() {
        showView(mNetworkErrorView);
    }

    public void showServerErrorView() {
        showView(mServerErrorView);
    }

    public void showEmptyView() {
        showView(mEmptyView);
    }

    public void showContentView() {
        showView(mRefreshView);
    }

    public void showLoadingView() {
        showView(mLoadingView);
    }

    private void showView(View view) {
        if (null != view) {
            for (int i = 0; i < this.getChildCount(); i++) {
                this.getChildAt(i).setVisibility(GONE);
            }
            view.setVisibility(VISIBLE);
        }
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public View getNetworkErrorView() {
        return mNetworkErrorView;
    }

    public View getServerErrorView() {
        return mServerErrorView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public boolean isReverse() {
        return reverseLayout;
    }

    public boolean isLoadingMore() {
        return mIsLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        mIsLoadMore = isLoadMore;
    }

    public void loadMore() {
        if (null != mOnLoadMoreListener) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setRefreshing(final boolean refreshing) {
        if (null != mRefreshView) {
            mRefreshView.post(new Runnable() {
                @Override
                public void run() {
                    if (null != mRefreshView) {
                        mRefreshView.setRefreshing(refreshing);
                    }
                }
            });
        }
    }

    public void setRefreshEnable(boolean enable) {
        mRefreshView.setEnabled(enable);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mRefreshView.setOnRefreshListener(listener);
    }

    public void hideRefreshOrLoadMore(boolean hideRefreshing,boolean hideLodeMore) {
        if (hideRefreshing) {
            setRefreshing(false);
        }
        if (hideLodeMore) {
            setIsLoadMore(false);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
