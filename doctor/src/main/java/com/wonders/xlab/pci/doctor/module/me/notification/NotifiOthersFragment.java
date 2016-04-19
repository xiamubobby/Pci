package com.wonders.xlab.pci.doctor.module.me.notification;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.NotifiOthersRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiOthersBean;
import com.wonders.xlab.pci.doctor.module.me.notification.otto.NotifiOthersOtto;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiOthersPresenter;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl.NotifiOthersPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 其他通知
 */
public class NotifiOthersFragment extends BaseFragment implements NotifiOthersPresenter.NotifiOthersPresenterListener {

    @Bind(R.id.recycler_view_notifi_others)
    CommonRecyclerView mRecyclerView;

    @Bind(R.id.card_notifi_others_bottom)
    CardView mCardViewBottom;

    private ObjectAnimator bottomMenuAnimatorIn;

    private ObjectAnimator bottomMenuAnimatorOut;


    private NotifiOthersRVAdapter mRVAdapter;

    private INotifiOthersPresenter mOthersPresenter;

    public NotifiOthersFragment() {
        // Required empty public constructor
    }

    public static NotifiOthersFragment getInstance() {
        return new NotifiOthersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notifi_others_fragment, container, false);
        ButterKnife.bind(this, view);
        mOthersPresenter = new NotifiOthersPresenter(this);
        addPresenter(mOthersPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomMenuAnimatorIn = ObjectAnimator.ofFloat(mCardViewBottom, "translationY", mCardViewBottom.getBottom(), mCardViewBottom.getTop());
        bottomMenuAnimatorIn.setDuration(1000);
        bottomMenuAnimatorIn.setInterpolator(new DecelerateInterpolator());
        bottomMenuAnimatorIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardViewBottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        bottomMenuAnimatorOut = ObjectAnimator.ofFloat(mCardViewBottom, "translationY", mCardViewBottom.getTranslationY(), mCardViewBottom.getBottom());
        bottomMenuAnimatorOut.setDuration(800);
        bottomMenuAnimatorOut.setInterpolator(new AccelerateInterpolator());
        bottomMenuAnimatorOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardViewBottom.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mOthersPresenter.getOthersNotifi(false, AIManager.getInstance().getDoctorId());
            }
        });
//        registerForContextMenu(mRecyclerView);

        mOthersPresenter.getOthersNotifi(true, AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "多选");
        menu.add(0, 1, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @OnClick(R.id.tv_notifi_others_cancel)
    public void cancel(View view) {
        mRVAdapter.showRadioButton(false);
        mRVAdapter.selectAll(false);
        bottomMenuAnimatorOut.setFloatValues(mCardViewBottom.getTranslationY(), mCardViewBottom.getBottom());
        bottomMenuAnimatorOut.start();
    }

    @OnClick(R.id.tv_notifi_others_select_all)
    public void selectAll(View view) {
        mRVAdapter.toggleSelect();
    }

    @OnClick(R.id.tv_notifi_others_delete)
    public void delete(View view) {
        /*RealmUtil.delectAllNotifiOthers();
        mRVAdapter.clear();*/
        mRVAdapter.deleteSelectedItem();
//        mRVAdapter.showRadioButton(false);
//        bottomMenuAnimatorOut.setFloatValues(mCardViewBottom.getTranslationY(), mCardViewBottom.getBottom());
//        bottomMenuAnimatorOut.start();
    }

    @Subscribe
    public void refreshPage(NotifiOthersOtto otto) {
        mOthersPresenter.getOthersNotifi(true, AIManager.getInstance().getDoctorId());
    }

    @Override
    public void showOthersNotifications(List<NotifiOthersBean> notifications) {
        initAdapter();
        mRVAdapter.setDatas(notifications);
    }

    @Override
    public void appendOthersNotifications(List<NotifiOthersBean> notifications) {
        initAdapter();
        int itemCount = mRVAdapter.getItemCount();
        mRVAdapter.appendDatas(notifications);
        if (mRVAdapter.getItemCount() > itemCount) {
            mRecyclerView.getRecyclerView().smoothScrollToPosition(itemCount);
        }
    }

    private void initAdapter() {
        if (null == mRVAdapter) {
            mRVAdapter = new NotifiOthersRVAdapter();
            mRVAdapter.setOnLongClickListener(new SimpleRVAdapter.OnLongClickListener() {
                @Override
                public void onItemLongClick(int position) {
                    mRVAdapter.showRadioButton(true);
                    bottomMenuAnimatorOut.setFloatValues(mCardViewBottom.getBottom(), mCardViewBottom.getTop());
                    bottomMenuAnimatorIn.start();
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mOthersPresenter.getOthersNotifi(true, AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mOthersPresenter.getOthersNotifi(true, AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mOthersPresenter.getOthersNotifi(true, AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

}
