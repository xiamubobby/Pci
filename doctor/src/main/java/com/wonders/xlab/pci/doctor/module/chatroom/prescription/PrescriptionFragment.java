package com.wonders.xlab.pci.doctor.module.chatroom.prescription;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter.PrescriptionRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.presenter.IPrescriptionPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.presenter.impl.PrescriptionPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrescriptionFragment extends BaseFragment implements PrescriptionPresenter.PrescriptionPresenterListener {

    @Bind(R.id.recycler_view_prescription)
    CommonRecyclerView mRecyclerView;

    private PrescriptionRVAdapter mRVAdapter;

    private IPrescriptionPresenter mPresenter;

    public PrescriptionFragment() {
        // Required empty public constructor
    }

    public static PrescriptionFragment newInstance(String patientId) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.prescription_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new PrescriptionPresenter(this);
        addPresenter(mPresenter);

        mPresenter.getPrescriptionList("", AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRVAdapter = null;
        ButterKnife.unbind(this);
    }

    @Override
    public void showPrescriptionList(List<PrescriptionBean> prescriptionBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new PrescriptionRVAdapter(getActivity());
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(prescriptionBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

    }
}
