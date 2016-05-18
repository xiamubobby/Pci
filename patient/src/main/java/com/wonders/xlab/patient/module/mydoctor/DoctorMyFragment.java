package com.wonders.xlab.patient.module.mydoctor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.alldoctor.AllDoctorActivity;
import com.wonders.xlab.patient.module.chatroom.ChatRoomActivity;
import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorItemBean;
import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorRVAdapter;
import com.wonders.xlab.patient.otto.BuyPackageSuccessOtto;
import com.wonders.xlab.patient.otto.DoctorTabChangeOtto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 我的医生界面
 */
public class DoctorMyFragment extends BaseFragment implements DoctorMyPresenterContract.ViewListener {

    @Bind(R.id.recycler_view_doctor_my)
    CommonRecyclerView mRecyclerView;

    private MyDoctorRVAdapter mMyDoctorRVAdapter;

    private DoctorMyPresenterContract.Actions mDoctorMyPresenter;

    public DoctorMyFragment() {
        // Required empty public constructor
    }

    public static DoctorMyFragment getInstance() {
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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), false);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mDoctorMyPresenter = DaggerDoctorMyComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .doctorMyModule(new DoctorMyModule(this))
                .build()
                .getDoctorMyPresenter();

        addPresenter(mDoctorMyPresenter);
        mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mMyDoctorRVAdapter = null;
    }

    @Override
    public void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        initMyDoctorAdapter();
        mMyDoctorRVAdapter.setDatas(myDoctorBeanList);
    }

    private void initMyDoctorAdapter() {
        if (null == mMyDoctorRVAdapter) {
            mMyDoctorRVAdapter = new MyDoctorRVAdapter();
            mMyDoctorRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.EXTRA_OWNER_ID, mMyDoctorRVAdapter.getBean(position).getOwnerId());
                    intent.putExtra(ChatRoomActivity.EXTRA_IM_GROUP_ID, mMyDoctorRVAdapter.getBean(position).getImGroupId());
                    intent.putExtra(ChatRoomActivity.EXTRA_GROUP_NAME, mMyDoctorRVAdapter.getBean(position).getDoctorGroupName());
                    intent.putExtra(ChatRoomActivity.EXTRA_CAN_CHAT, MyDoctorItemBean.TYPE_IN_SERVICE == mMyDoctorRVAdapter.getBean(position).getType());
                    startActivity(intent);
                }
            });
            StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(mMyDoctorRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(stickyRecyclerHeadersDecoration);
            mRecyclerView.setAdapter(mMyDoctorRVAdapter);
        }
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
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(getActivity(), AllDoctorActivity.class));
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
        OttoManager.post(new DoctorTabChangeOtto(1));
    }

    @Override
    public void showErrorToast(String message) {
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
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mDoctorMyPresenter.getMyDoctors(AIManager.getInstance().getPatientId(), true);

            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
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
        mRecyclerView.hideRefreshOrLoadMore(true, true);
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
