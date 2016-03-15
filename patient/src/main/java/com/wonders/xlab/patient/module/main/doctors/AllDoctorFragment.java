package com.wonders.xlab.patient.module.main.doctors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.doctors.adapter.AllDoctorRVAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;
import com.wonders.xlab.patient.mvp.presenter.AllDoctorPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.IAllDoctorPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllDoctorFragment extends BaseFragment implements IAllDoctorPresenter {

    @Bind(R.id.recycler_view_doctor_all)
    PullLoadMoreRecyclerView mRecyclerView;

    private AllDoctorPresenter mAllDoctorPresenter;
    private AllDoctorRVAdapter mAllDoctorRVAdapter;

    public AllDoctorFragment() {
        // Required empty public constructor
    }

    public static AllDoctorFragment newInstance() {
        return new AllDoctorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.doctor_all_fragment, container, false);
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
                mAllDoctorPresenter.getAllDoctor();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "onLoadMore", Toast.LENGTH_SHORT).show();
            }
        });

        mAllDoctorPresenter = new AllDoctorPresenter(this);
        mAllDoctorPresenter.getAllDoctor();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        if (null == mAllDoctorRVAdapter) {
            mAllDoctorRVAdapter = new AllDoctorRVAdapter();
        }
        mAllDoctorRVAdapter.setDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mAllDoctorRVAdapter);
    }

    @Override
    public void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList) {
        if (null == mAllDoctorRVAdapter) {
            mAllDoctorRVAdapter = new AllDoctorRVAdapter();
        }
        mAllDoctorRVAdapter.appendDatas(myDoctorBeanList);
        mRecyclerView.setAdapter(mAllDoctorRVAdapter);
    }
}
