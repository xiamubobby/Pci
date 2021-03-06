package im.hua.uikit.crv;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import im.hua.uikit.R;

/**
 * Created by hua on 16/3/24.
 */
public class CommonRecyclerView extends FrameLayout {
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final int MAX_ALPHA = 255;

    private OnLoadMoreListener mOnLoadMoreListener;

    private final int LAYOUT_MANGER_LINEAR = 0;
    private final int LAYOUT_MANGER_GRID = 1;
    private final int LAYOUT_MANGER_STAGGERED_GRID = 2;

    private View mLoadingView;
    private View mEmptyView;
    private View mNetworkErrorView;
    private View mServerErrorView;
    private View mLoadMoreView;

    private int layoutManager;
    private boolean reverseLayout;
    private int spanCount;
    private int orientation;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshView;

    private boolean mIsLoadMore;
    private boolean mIsRefreshing;

    /**
     * 正在显示的View
     */
    private View mShowingView;

    /**
     *
     */
    public static final int HANDLE_VIEW_ID_NONE = -1;

    private LayoutInflater mLayoutInflater;


    private MaterialProgressDrawable mProgress;
    private TranslateAnimation showNoReverseAnimation;

    private TranslateAnimation hideNoReverseAnimation;

    private TranslateAnimation showReverseAnimation;

    private TranslateAnimation hideReverseAnimation;

    private int mEmptyResourceId;
    private int mLoadingResId;
    private int mNetErrResId;
    private int mServerErrResId;


    private void initShowNoReverseAnimation() {
        if (null == showNoReverseAnimation) {
            showNoReverseAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, -0.4f);
        }
    }

    private void initHideNoReverseAnimation() {
        if (null == hideNoReverseAnimation) {
            hideNoReverseAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -0.4f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
    }

    private void initShowReverseAnimation() {
        if (null == showReverseAnimation) {
            showReverseAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.4f);
        }
    }

    private void initHideReverseAnimation() {
        if (null == hideReverseAnimation) {
            hideReverseAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.4f, Animation.RELATIVE_TO_SELF, -1.0f);
        }
    }

    private Animation fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);

    public CommonRecyclerView(Context context) {
        this(context, null);
    }

    public CommonRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutInflater = LayoutInflater.from(context);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonRecyclerView);

        mEmptyResourceId = array.getResourceId(R.styleable.CommonRecyclerView_emptyView, -1);

        mLoadingResId = array.getResourceId(R.styleable.CommonRecyclerView_loadingView, -1);

        mNetErrResId = array.getResourceId(R.styleable.CommonRecyclerView_networkErrorView, -1);

        mServerErrResId = array.getResourceId(R.styleable.CommonRecyclerView_serverErrorView, -1);

        int loadMoreResId = array.getResourceId(R.styleable.CommonRecyclerView_loadMoreView, -1);
        if (-1 != loadMoreResId) {
            mLoadMoreView = mLayoutInflater.inflate(loadMoreResId, null, false);
        }
        layoutManager = array.getInteger(R.styleable.CommonRecyclerView_crvLayoutManager, LAYOUT_MANGER_LINEAR);
        spanCount = array.getInteger(R.styleable.CommonRecyclerView_crvSpanCount, 1);
        orientation = array.getInteger(R.styleable.CommonRecyclerView_orientation, RecyclerView.VERTICAL);
        reverseLayout = array.getBoolean(R.styleable.CommonRecyclerView_crvReverseLayout, false);

        array.recycle();

        mRefreshView = (SwipeRefreshLayout) mLayoutInflater.inflate(R.layout.crv_recycler_view, null, false);
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

        if (null != mRefreshView) {
            this.addView(mRefreshView);
            mShowingView = mRefreshView;
        }
    }

    private void setupLoadMoreView() {
        createProgressView();

        if (null == mLoadMoreView) {
            return;
        }
        this.addView(mLoadMoreView);
        mLoadMoreView.setVisibility(INVISIBLE);

        LayoutParams params = (LayoutParams) mLoadMoreView.getLayoutParams();
        int size = dp2px(40);
        if (params == null) {
            params = new LayoutParams(size, size);
        } else {
            params.width = size;
            params.height = size;
        }
        if (mLoadMoreView instanceof CardView) {
            CardView cardView = (CardView) mLoadMoreView;
            cardView.setRadius(dp2px(20));
        }
        if (reverseLayout) {
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
            mLoadMoreView.setTop(-size);
        } else {
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            mLoadMoreView.setTop(getMeasuredHeight());
        }
        mLoadMoreView.setLayoutParams(params);
        initLoadMoreAnimation();
    }

    private void createProgressView() {
        CircleImageView circleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT, CIRCLE_DIAMETER / 2);
        mProgress = new MaterialProgressDrawable(getContext(), this);
        mProgress.updateSizes(MaterialProgressDrawable.DEFAULT);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        circleView.setImageDrawable(mProgress);
        circleView.setVisibility(View.VISIBLE);

        if (mLoadMoreView == null) {
            mLoadMoreView = circleView;
        }
    }

    private void initLoadMoreAnimation() {
        /*
      加载更多view动画
      正序
     */
        long loadMoreAnimationDuration = 400;
        initHideNoReverseAnimation();
        hideNoReverseAnimation.setDuration(loadMoreAnimationDuration);
        hideNoReverseAnimation.setFillAfter(true);
        hideNoReverseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadMoreView.setVisibility(View.INVISIBLE);
                mProgress.stop();
                setIsLoadMore(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        initHideReverseAnimation();
        hideReverseAnimation.setDuration(loadMoreAnimationDuration);
        hideReverseAnimation.setFillAfter(true);
        hideReverseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadMoreView.setVisibility(View.INVISIBLE);
                mProgress.stop();
                setIsLoadMore(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        initShowNoReverseAnimation();
        showNoReverseAnimation.setDuration(loadMoreAnimationDuration);
        showNoReverseAnimation.setFillAfter(true);
        showNoReverseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mProgress.setAlpha(MAX_ALPHA);
                mProgress.start();
                mLoadMoreView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        initShowReverseAnimation();
        showReverseAnimation.setDuration(loadMoreAnimationDuration);
        showReverseAnimation.setFillAfter(true);
        showReverseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mProgress.setAlpha(MAX_ALPHA);
                mProgress.start();
                mLoadMoreView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
                observeDataChange();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                observeDataChange();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                observeDataChange();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                observeDataChange();
            }

            private void observeDataChange() {
                if (adapter.getItemCount() > 0) {
                    showContentView();
                } else {
                    showEmptyView(null, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
                }
            }


        });
    }

    /**
     * 显示网络错误界面
     *
     * @param listener
     * @param handleViewId
     */
    public void showNetworkErrorView(@Nullable final OnNetworkErrorViewClickListener listener, @IdRes int handleViewId) {
        if (-1 != mNetErrResId && null == mNetworkErrorView) {
            mNetworkErrorView = mLayoutInflater.inflate(mNetErrResId, null, false);
        }
        showView(mNetworkErrorView);
        if (null != listener && null != mNetworkErrorView) {
            if (handleViewId != HANDLE_VIEW_ID_NONE) {
                View viewById = mNetworkErrorView.findViewById(handleViewId);
                if (null == viewById) {
                    throw new IllegalArgumentException("请确保NetworkErrorView中存在Id为handleViewId的View");
                }
                viewById.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingView();
                        listener.onClick();
                    }
                });
            } else {
                mNetworkErrorView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingView();
                        listener.onClick();
                    }
                });
            }
        }
    }

    /**
     * 显示服务器错误界面
     *
     * @param listener
     * @param handleViewId
     */
    public void showServerErrorView(@Nullable final OnServerErrorViewClickListener listener, @IdRes int handleViewId) {
        if (-1 != mServerErrResId && null == mServerErrorView) {
            mServerErrorView = mLayoutInflater.inflate(mServerErrResId, null, false);
        }
        showView(mServerErrorView);
        if (null != listener && mServerErrorView != null) {
            if (handleViewId != HANDLE_VIEW_ID_NONE) {
                View viewById = mServerErrorView.findViewById(handleViewId);
                if (null == viewById) {
                    throw new IllegalArgumentException("请确保ServerErrorView中存在Id为handleViewId的View");
                }
                viewById.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingView();
                        listener.onClick();
                    }
                });
            } else {
                mServerErrorView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingView();
                        listener.onClick();
                    }
                });
            }
        }
    }


    /**
     * 显示空数据界面
     *
     * @param listener
     * @param autoShowLoading
     * @param handleViewId
     */
    public void showEmptyView(@Nullable final OnEmptyViewClickListener listener, final boolean autoShowLoading, @IdRes int handleViewId) {
        if (mEmptyResourceId != -1 && null == mEmptyView) {
            mEmptyView = mLayoutInflater.inflate(mEmptyResourceId, null, false);
        }
        showView(mEmptyView);
        if (null != listener && null != mEmptyView) {
            if (handleViewId != HANDLE_VIEW_ID_NONE) {
                View viewById = mEmptyView.findViewById(handleViewId);
                if (null == viewById) {
                    throw new IllegalArgumentException("请确保EmptyView中存在Id为handleViewId的View");
                }
                viewById.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (autoShowLoading) {
                            showLoadingView();
                        }
                        listener.onClick();
                    }
                });
            } else {
                mEmptyView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (autoShowLoading) {
                            showLoadingView();
                        }
                        listener.onClick();
                    }
                });
            }

        }
    }

    /**
     * 显示RecyclerView
     */
    public void showContentView() {
        showView(mRefreshView);
    }

    /**
     * 显示加载界面
     */
    public void showLoadingView() {
        if (-1 != mLoadingResId && null == mLoadingView) {
            mLoadingView = mLayoutInflater.inflate(mLoadingResId, null, false);
        }
        showView(mLoadingView);
    }

    private synchronized void showView(final View view) {
        if (null == mShowingView || null == view || mShowingView == view) {
            return;
        }
        if (mShowingView != mRefreshView) {
            removeView(mShowingView);
        }
        if (view != mRefreshView) {
            mRefreshView.setVisibility(GONE);
            addView(view);
        }
        view.setVisibility(VISIBLE);
        view.startAnimation(fadeIn);
        mShowingView = view;
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
            showLoadMore();
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setRefreshing(final boolean refreshing) {
        mIsRefreshing = refreshing;
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

    public void setOnRefreshListener(final SwipeRefreshLayout.OnRefreshListener listener) {
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsRefreshing = true;
                listener.onRefresh();
                hideLoadMore();
            }
        });
    }

    public void hideRefreshOrLoadMore(boolean hideRefreshing, boolean hideLodeMore) {
        if (hideRefreshing && null != mRefreshView) {
            setRefreshing(false);
        }
        if (hideLodeMore && isLoadingMore()) {
            hideLoadMore();
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        if (null != onLoadMoreListener) {
            setupLoadMoreView();
        }
    }

    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    public synchronized void hideLoadMore() {
        if (!isLoadingMore()) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (reverseLayout) {
                    initHideReverseAnimation();
                    mLoadMoreView.startAnimation(hideReverseAnimation);
                } else {
                    initHideNoReverseAnimation();
                    mLoadMoreView.startAnimation(hideNoReverseAnimation);
                }
            }
        }, 1000);

    }

    public synchronized void showLoadMore() {
        mLoadMoreView.clearAnimation();
        setIsLoadMore(true);
        if (reverseLayout) {
            initShowReverseAnimation();
            mLoadMoreView.startAnimation(showReverseAnimation);
        } else {
            initShowNoReverseAnimation();
            mLoadMoreView.startAnimation(showNoReverseAnimation);
        }
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public interface OnEmptyViewClickListener {
        void onClick();
    }

    public interface OnNetworkErrorViewClickListener {
        void onClick();
    }

    public interface OnServerErrorViewClickListener {
        void onClick();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}
