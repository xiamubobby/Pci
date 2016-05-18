package com.wonders.xlab.pci.doctor.module.patientlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.patientinfo.PatientInfoContainerActivity;
import com.wonders.xlab.pci.doctor.module.patientlist.adapter.PatientRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientlist.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.PatientPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends BaseFragment implements PatientPresenter.PatientPresenterListener {

    private PatientPresenter mPatientPresenter;

    @Bind(R.id.recycler_view_patient)
    CommonRecyclerView mRecyclerView;

    private PatientRVAdapter mPatientRVAdapter;

    public PatientFragment() {
        // Required empty public constructor
    }

    public static PatientFragment newInstance() {
        return new PatientFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patient_fragment, container, false);
        ButterKnife.bind(this, view);

        mPatientPresenter = new PatientPresenter(this);
        addPresenter(mPatientPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPatientPresenter.getPatientList(AIManager.getInstance().getDoctorId());
            }
        });

        mPatientPresenter.getPatientList(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initPatientAdapter() {
        if (mPatientRVAdapter == null) {
            mPatientRVAdapter = new PatientRVAdapter();
            mPatientRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), PatientInfoContainerActivity.class);
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_PATIENT_ID, mPatientRVAdapter.getBean(position).getPatientId());
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_PATIENT_NAME, mPatientRVAdapter.getBean(position).getPatientName());
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_PATIENT_PHONE_NUMBER, mPatientRVAdapter.getBean(position).getPhoneNumber());
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_OWNER_ID, mPatientRVAdapter.getBean(position).getOwnerId());
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_IM_GROUP_ID, mPatientRVAdapter.getBean(position).getImGroupId());
                    intent.putExtra(PatientInfoContainerActivity.EXTRA_GROUP_NAME, mPatientRVAdapter.getBean(position).getGroupName());
                    getActivity().startActivity(intent);
                }
            });
        }
        mRecyclerView.setAdapter(mPatientRVAdapter);
    }

    @Override
    public void showPatientList(ArrayList<PatientBean> patientBeen) {
        /**
         * TODO 一定要通过这样设置加载完成
         */
        initPatientAdapter();
        mPatientRVAdapter.setDatas(patientBeen);
    }

    @Override
    public void appendPatientList(ArrayList<PatientBean> patientBeen) {
        /**
         * TODO 一定要通过这样设置加载完成
         */
        initPatientAdapter();
        mPatientRVAdapter.appendDatas(patientBeen);
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
                mPatientPresenter.getPatientList(AIManager.getInstance().getDoctorId());
            }
        }, im.hua.uikit.R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mPatientPresenter.getPatientList(AIManager.getInstance().getDoctorId());
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mPatientPresenter.getPatientList(AIManager.getInstance().getDoctorId());
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }
}
