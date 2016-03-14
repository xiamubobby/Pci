package com.wonders.xlab.patient.module.main.doctors;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.doctors.adapter.MyDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.MyDoctorPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IMyDoctorPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.utils.DateUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDoctorFragment extends BaseFragment implements IMyDoctorPresenter {

    @Bind(R.id.recycler_view_doctor_my)
    UltimateRecyclerView mRecyclerView;

    private MyDoctorRVAdapter mMyDoctorRVAdapter;

    private MyDoctorPresenter mMyDoctorPresenter;

    public MyDoctorFragment() {
        // Required empty public constructor
    }

    public static MyDoctorFragment newInstance() {
        return new MyDoctorFragment();
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.enableDefaultSwipeRefresh(true);
        mRecyclerView.enableLoadmore();
        mRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        MyDoctorItemBean itemBean = new MyDoctorItemBean();
                        itemBean.setHeaderId(MyDoctorItemBean.HEADER_ID_OUT_OF_SERVICE);
                        itemBean.setDoctorGroupName("新增的");
                        itemBean.setLatestChatMessage("可以复检了。");
                        itemBean.setTimeStr(DateUtil.format(Calendar.getInstance().getTimeInMillis(), "HH:mm"));
                        itemBean.setPortraitUrl(Constant.DEFAULT_PORTRAIT);
                        mMyDoctorRVAdapter.appendToLast(itemBean);

                        mRecyclerView.reenableLoadmore(LayoutInflater.from(getActivity()).inflate(R.layout.footer_layout, null));
                    }
                }, 2500);

            }
        });

        mMyDoctorPresenter = new MyDoctorPresenter(this);
        addPresenter(mMyDoctorPresenter);

        mMyDoctorPresenter.getMyDoctorList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        if (null == mMyDoctorRVAdapter) {
            mMyDoctorRVAdapter = new MyDoctorRVAdapter();
            mMyDoctorRVAdapter.enableLoadMore(true);
            mMyDoctorRVAdapter.setCustomLoadMoreView(LayoutInflater.from(getActivity()).inflate(R.layout.footer_layout, null));
            StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(mMyDoctorRVAdapter);
            mRecyclerView.addItemDecoration(stickyRecyclerHeadersDecoration);
        }
        mMyDoctorRVAdapter.setDataList(myDoctorBeanList);
        mRecyclerView.setAdapter(mMyDoctorRVAdapter);
    }

    @Override
    public void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        if (null == mMyDoctorRVAdapter) {
            mMyDoctorRVAdapter = new MyDoctorRVAdapter();
        }
        mMyDoctorRVAdapter.appendDataList(myDoctorBeanList);
        mRecyclerView.setAdapter(mMyDoctorRVAdapter);
    }
}
