package com.wonders.xlab.pci.doctor.module.patientinfo.prescription;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter.PrescriptionRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.PrescriptionPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrescriptionFragment extends BaseFragment implements PrescriptionPresenterContract.ViewListener {

    private static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_prescription)
    CommonRecyclerView mRecyclerView;

    private PrescriptionRVAdapter mRVAdapter;

    private PrescriptionPresenterContract mPresenter;

    public PrescriptionFragment() {
        // Required empty public constructor
    }

    public static PrescriptionFragment newInstance(String patientId) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (null == data) {
            Toast.makeText(getActivity(), "获取处方清单失败", Toast.LENGTH_SHORT).show();
        }
        mPatientId = data.getString(ARG_PATIENT_ID);


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

//        mPresenter =
//        addPresenter(mPresenter);

//
//        mPresenter.getPrescriptionList("", AIManager.getInstance().getDoctorId());
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
    public void appendPrescriptionList(List<PrescriptionBean> prescriptionBeanList) {

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
