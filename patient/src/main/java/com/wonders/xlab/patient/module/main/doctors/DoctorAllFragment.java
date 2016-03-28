package com.wonders.xlab.patient.module.main.doctors;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.main.doctors.adapter.AllDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.module.main.doctors.detail.DoctorDetailActivity;
import com.wonders.xlab.patient.mvp.presenter.IDoctorAllPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.DoctorAllPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 所有医生
 */
public class DoctorAllFragment extends BaseFragment implements DoctorAllPresenter.DoctorAllPresenterListener {

    @Bind(R.id.recycler_view_doctor_all)
    PullLoadMoreRecyclerView mRecyclerView;

    private IDoctorAllPresenter mDoctorAllPresenter;

    private AllDoctorRVAdapter mAllDoctorRVAdapter;

    public DoctorAllFragment() {
        // Required empty public constructor
    }

    public static DoctorAllFragment newInstance() {
        return new DoctorAllFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.doctor_all_fragment, container, false);
        ButterKnife.bind(this, view);

        mDoctorAllPresenter = new DoctorAllPresenter(this);
        addPresenter(mDoctorAllPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mDoctorAllPresenter.getAllDoctors(AIManager.getInstance(getActivity()).getPatientId(), true);
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "onLoadMore", Toast.LENGTH_SHORT).show();
            }
        });

        mDoctorAllPresenter.getAllDoctors(AIManager.getInstance(getActivity()).getPatientId(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        initRecyclerViewAdapter();
        mAllDoctorRVAdapter.setDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mAllDoctorRVAdapter);
    }

    private void initRecyclerViewAdapter() {
        if (null == mAllDoctorRVAdapter) {
            mAllDoctorRVAdapter = new AllDoctorRVAdapter();
            mAllDoctorRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
                    intent.putExtra(DoctorDetailActivity.EXTRA_TITLE, mAllDoctorRVAdapter.getBean(position).getDoctorGroupName());
                    intent.putExtra(DoctorDetailActivity.EXTRA_ID, mAllDoctorRVAdapter.getBean(position).getGroupId());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        initRecyclerViewAdapter();
        mAllDoctorRVAdapter.appendDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mAllDoctorRVAdapter);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.setPullLoadMoreCompleted();
    }
}
