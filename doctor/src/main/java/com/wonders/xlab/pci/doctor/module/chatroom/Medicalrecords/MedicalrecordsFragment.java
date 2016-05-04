package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.data.presenter.impl.MedicalRecordPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.MedicalRecordsRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecords.presenter.impl.MedicalRecordsPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 */
public class MedicalRecordsFragment extends BaseFragment implements MedicalRecordsPresenter.MedicalRecordsPresenterListener {

    @Bind(R.id.recycler_view_medicalRecords)
    public CommonRecyclerView mRecycler;

    MedicalRecordsRVAdapter mRVAdapter;

    MedicalRecordsPresenter mPresenter;

    public static MedicalRecordsFragment newInstance(String patientId) {
        MedicalRecordsFragment fragment = new MedicalRecordsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medical_records_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new MedicalRecordsPresenter(this);
        addPresenter(mPresenter);
        mPresenter.getMedicalRecordsList("", AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;

    }

    @Override
    public void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList) {
        if (mRVAdapter == null) {
            mRVAdapter = new MedicalRecordsRVAdapter(getActivity());
            mRecycler.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(medicalRecordsBeanList);
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
