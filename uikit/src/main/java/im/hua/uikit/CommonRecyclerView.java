package im.hua.uikit;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hua on 16/3/24.
 * 为了兼容SwipeRefresh的下拉刷新效果，此处继承NestedScrollView
 */
public class CommonRecyclerView extends NestedScrollView {
    private final int LAYOUT_MANGER_LINEAR = 0;
    private final int LAYOUT_MANGER_GRID = 1;
    private final int LAYOUT_MANGER_STAGGERED_GRID = 2;

    private FrameLayout containerView;

    private View mLoadingView;
    private View mEmptyView;
    private View mNetworkErrorView;
    private View mServerErrorView;

    private int layoutManager;
    private boolean reverseLayout;
    private int spanCount;
    private int orientation;

    private RecyclerView mRecyclerView;


    public CommonRecyclerView(Context context) {
        this(context, null);
    }

    public CommonRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        containerView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.crv_container, null);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonRecyclerView, defStyleAttr, 0);

        mEmptyView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_emptyView, R.layout.crv_empty), null);
        mLoadingView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_loadingView, R.layout.crv_loading), null);
        mNetworkErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_networkErrorView, R.layout.crv_network_error), null);
        mServerErrorView = LayoutInflater.from(context).inflate(array.getResourceId(R.styleable.CommonRecyclerView_serverErrorView, R.layout.crv_server_error), null);
        layoutManager = array.getInteger(R.styleable.CommonRecyclerView_crvLayoutManager, LAYOUT_MANGER_LINEAR);
        spanCount = array.getInteger(R.styleable.CommonRecyclerView_crvSpanCount, 1);
        orientation = array.getInteger(R.styleable.CommonRecyclerView_orientation, RecyclerView.VERTICAL);
        reverseLayout = array.getBoolean(R.styleable.CommonRecyclerView_crvReverseLayout, false);

        array.recycle();

        if (null != mNetworkErrorView) {
            mNetworkErrorView.setVisibility(GONE);
            containerView.addView(mNetworkErrorView);
        }
        if (null != mServerErrorView) {
            mServerErrorView.setVisibility(GONE);
            containerView.addView(mServerErrorView);
        }
        if (null != mEmptyView) {
            containerView.addView(mEmptyView);
        }
        if (null != mLoadingView) {
            containerView.addView(mLoadingView);
        }
        mRecyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.crv_recycler_view, null);
        if (null != mRecyclerView) {
            mRecyclerView.setVisibility(GONE);
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
            containerView.addView(mRecyclerView);

        }
        this.addView(containerView);
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
        containerView.setVisibility(VISIBLE);
        if (view != null) {
            for (int i = 0; i < containerView.getChildCount(); i++) {
                containerView.getChildAt(i).setVisibility(GONE);
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
}
