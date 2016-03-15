package com.wonders.xlab.patient.module.main.doctors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.doctors.adapter.MyDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.MyDoctorPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IMyDoctorPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDoctorFragment extends BaseFragment implements IMyDoctorPresenter {

    @Bind(R.id.recycler_view_doctor_my)
    PullLoadMoreRecyclerView mRecyclerView;

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
        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "onLoadMore", Toast.LENGTH_SHORT).show();
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
            StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(mMyDoctorRVAdapter);
            mRecyclerView.getRecyclerView().addItemDecoration(stickyRecyclerHeadersDecoration);
        }
        mMyDoctorRVAdapter.setDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mMyDoctorRVAdapter);
    }

    @Override
    public void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList) {
        if (null == mMyDoctorRVAdapter) {
            mMyDoctorRVAdapter = new MyDoctorRVAdapter();
        }
        mMyDoctorRVAdapter.appendDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mMyDoctorRVAdapter);
    }
}
