package com.wonders.xlab.patient.module.main.doctors;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.chatroom.ChatRoomActivity;
import com.wonders.xlab.patient.module.main.doctors.adapter.MyDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.IDoctorMyPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorMyPresenter;
import com.wonders.xlab.patient.otto.BuyPackageSuccessOtto;
import com.wonders.xlab.patient.otto.DoctorTabChangeOtto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 我的医生界面
 */
public class DoctorMyFragment extends BaseFragment implements DoctorMyPresenter.DoctorMyPresenterListener {

    @Bind(R.id.recycler_view_doctor_my)
    PullLoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.empty)
    View mEmptyView;

    private MyDoctorRVAdapter mMyDoctorRVAdapter;

    private IDoctorMyPresenter mDoctorMyPresenter;

    public DoctorMyFragment() {
        // Required empty public constructor
    }

    public static DoctorMyFragment newInstance() {
        return new DoctorMyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OttoManager.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.doctor_my_fragment, container, false);
        ButterKnife.bind(this, view);

        mDoctorMyPresenter = new DoctorMyPresenter(this);
        addPresenter(mDoctorMyPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
            }

            @Override
            public void onLoadMore() {
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), false);
            }
        });
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setRefreshing(true);
            }
        });
        mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        initMyDoctorAdapter();
        mMyDoctorRVAdapter.setDatas(myDoctorBeanList);
    }

    private void initMyDoctorAdapter() {
        if (null == mMyDoctorRVAdapter) {
            mMyDoctorRVAdapter = new MyDoctorRVAdapter();
            mMyDoctorRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.EXTRA_GROUP_ID, mMyDoctorRVAdapter.getBean(position).getGroupId());
                    intent.putExtra(ChatRoomActivity.EXTRA_IM_GROUP_ID, mMyDoctorRVAdapter.getBean(position).getImGroupId());
                    intent.putExtra(ChatRoomActivity.EXTRA_GROUP_NAME, mMyDoctorRVAdapter.getBean(position).getDoctorGroupName());
                    intent.putExtra(ChatRoomActivity.EXTRA_CAN_CHAT, MyDoctorItemBean.TYPE_IN_SERVICE == mMyDoctorRVAdapter.getBean(position).getType());
                    startActivity(intent);
                }
            });
            StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(mMyDoctorRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(stickyRecyclerHeadersDecoration);
        }
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mMyDoctorRVAdapter);
    }

    @Override
    public void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        initMyDoctorAdapter();
        mMyDoctorRVAdapter.appendDatas(myDoctorBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        OttoManager.post(new DoctorTabChangeOtto(1));
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Subscribe
    public void buyPackageSuccess(BuyPackageSuccessOtto otto) {
        mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
    }

    @Override
    public void hideLoading() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.setPullLoadMoreCompleted();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_doctors_my));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_doctors_my));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
    }
}
