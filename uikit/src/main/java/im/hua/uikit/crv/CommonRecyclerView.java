package im.hua.uikit.crv;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import im.hua.uikit.R;

/**
 * Created by hua on 16/3/24.
 * 默认disable下拉刷新
 */
public class CommonRecyclerView extends SwipeRefreshLayout {
    private final int LAYOUT_MANGER_LINEAR = 0;
    private final int LAYOUT_MANGER_GRID = 1;
    private final int LAYOUT_MANGER_STAGGERED_GRID = 2;

    private NestedScrollView containerView;

    private View mLoadingView;
    private View mEmptyView;
    private View mNetworkErrorView;
    private View mServerErrorView;

    private int layoutManager;
    private boolean reverseLayout;
    private int spanCount;
    private int orientation;

    private RecyclerView mRecyclerView;
    /**
     * 是否正在加载
     */
    private boolean mIsLoadMore;


    public CommonRecyclerView(Context context) {
        this(context, null);
    }

    public CommonRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEnabled(false);

        containerView = (NestedScrollView) LayoutInflater.from(context).inflate(R.layout.crv_container, null,false);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonRecyclerView);

        mEmptyView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_emptyView, R.layout.crv_empty), null,false);
        mLoadingView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_loadingView, R.layout.crv_loading), null,false);
        mNetworkErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_networkErrorView, R.layout.crv_network_error), null,false);
        mServerErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_serverErrorView, R.layout.crv_server_error), null,false);
        layoutManager = array.getInteger(R.styleable.CommonRecyclerView_crvLayoutManager, LAYOUT_MANGER_LINEAR);
        spanCount = array.getInteger(R.styleable.CommonRecyclerView_crvSpanCount, 1);
        orientation = array.getInteger(R.styleable.CommonRecyclerView_orientation, RecyclerView.VERTICAL);
        reverseLayout = array.getBoolean(R.styleable.CommonRecyclerView_crvReverseLayout, false);

        array.recycle();

        mRecyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.crv_recycler_view, null,false);
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

        /*if (null != mLoadingView) {
            containerView.addView(mLoadingView);
            mLoadingView.setVisibility(GONE);
        }
        if (null != mNetworkErrorView) {
            containerView.addView(mNetworkErrorView);
            mNetworkErrorView.setVisibility(GONE);
        }
        if (null != mServerErrorView) {
            containerView.addView(mServerErrorView);
            mServerErrorView.setVisibility(GONE);
        }
        if (null != mRecyclerView) {
            containerView.addView(mRecyclerView);
            mRecyclerView.setVisibility(GONE);
        }*/
        if (null != mRecyclerView) {
            containerView.addView(mRecyclerView);
        }
        addView(containerView);
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

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (null != mRecyclerView) {
            mRecyclerView.setAdapter(adapter);
        }
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

    public void showRecyclerView() {
        showView(mRecyclerView);
    }

    public void showLoadingView() {
        showView(mLoadingView);
    }

    private void showView(View view) {
        if (null != view) {
            /*for (int i = 0; i < containerView.getChildCount(); i++) {
                containerView.getChildAt(i).setVisibility(GONE);
            }*/
            containerView.removeAllViews();
            containerView.addView(view);
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
        Toast.makeText(getContext(), "loadmore", Toast.LENGTH_SHORT).show();
    }
}
