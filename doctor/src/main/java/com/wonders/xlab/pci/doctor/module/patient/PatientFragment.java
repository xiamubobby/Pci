package com.wonders.xlab.pci.doctor.module.patient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patient.adapter.PatientRVAdapter;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.module.patient.model.PatientModel;
import com.wonders.xlab.pci.doctor.module.patient.view.PatientView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends BaseFragment implements PatientView {

    private PatientModel mPatientModel;

    @Bind(R.id.recycler_view_patient)
    RecyclerView mRecyclerViewPatient;
    @Bind(R.id.refresh_patient)
    SwipeRefreshLayout mRefreshPatient;

    private PatientRVAdapter mPatientRVAdapter;

    public PatientFragment() {
        // Required empty public constructor
    }

    public static PatientFragment newInstance() {
        return new PatientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPatientModel = new PatientModel(this);
        addModel(mPatientModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patient_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //Fri Feb 19 01:28:03 EST 2016

    //1455863283715
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewPatient.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerViewPatient.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider)));

        mPatientModel.getPatientList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initPatientAdapter() {
        if (mPatientRVAdapter == null) {
            mPatientRVAdapter = new PatientRVAdapter();
            mPatientRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                }
            });
        }
        mRecyclerViewPatient.setAdapter(mPatientRVAdapter);
    }

    @Override
    public void showPatientList(ArrayList<PatientBean> patientBeen) {
        initPatientAdapter();
        mPatientRVAdapter.setDatas(patientBeen);
    }

    @Override
    public void appendPatientList(ArrayList<PatientBean> patientBeen) {
        initPatientAdapter();
        mPatientRVAdapter.appendDatas(patientBeen);
    }

    @Override
    public void showLoading() {
        setSwipeRefresh(true);
    }

    @Override
    public void hideLoading() {
        setSwipeRefresh(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setSwipeRefresh(final boolean refresh) {
        mRefreshPatient.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshPatient != null) {
                    mRefreshPatient.setRefreshing(refresh);
                }
            }
        });
    }

}
