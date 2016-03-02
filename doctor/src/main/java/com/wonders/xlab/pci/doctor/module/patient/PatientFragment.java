package com.wonders.xlab.pci.doctor.module.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.chatroom.ChatRoomActivity;
import com.wonders.xlab.pci.doctor.module.patient.adapter.PatientRVAdapter;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IPatientPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.PatientPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends BaseFragment implements IPatientPresenter {

    private PatientPresenter mPatientPresenter;

    @Bind(R.id.recycler_view_patient)
    PullLoadMoreRecyclerView mRecyclerViewPatient;

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
        mPatientPresenter = new PatientPresenter(this);
        addPresenter(mPatientPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.patient_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewPatient.setLinearLayout(false);
        mRecyclerViewPatient.getRecyclerView().addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerViewPatient.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPatientPresenter.getPatientList(AIManager.getInstance(getActivity()).getUserId());
            }

            @Override
            public void onLoadMore() {

            }
        });

        mRecyclerViewPatient.setRefreshing(true);
        mPatientPresenter.getPatientList(AIManager.getInstance(getActivity()).getUserId());
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
                    Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.EXTRA_PATIENT_ID, mPatientRVAdapter.getBean(position).getPatientId());
                    intent.putExtra(ChatRoomActivity.EXTRA_PATIENT_NAME, mPatientRVAdapter.getBean(position).getPatientName());
                    intent.putExtra(ChatRoomActivity.EXTRA_PATIENT_PHONE_NUMBER, mPatientRVAdapter.getBean(position).getPhoneNumber());
                    intent.putExtra(ChatRoomActivity.EXTRA_PATIENT_GROUP_ID, mPatientRVAdapter.getBean(position).getGroupId());
                    getActivity().startActivity(intent);
                }
            });
        }
        mRecyclerViewPatient.setAdapter(mPatientRVAdapter);
    }

    @Override
    public void showPatientList(ArrayList<PatientBean> patientBeen) {
        /**
         * TODO 一定要通过这样设置加载完成
         */
        mRecyclerViewPatient.setPullLoadMoreCompleted();
        initPatientAdapter();
        mPatientRVAdapter.setDatas(patientBeen);
    }

    @Override
    public void appendPatientList(ArrayList<PatientBean> patientBeen) {
        /**
         * TODO 一定要通过这样设置加载完成
         */
        mRecyclerViewPatient.setPullLoadMoreCompleted();
        initPatientAdapter();
        mPatientRVAdapter.appendDatas(patientBeen);
    }

    @Override
    public void showError(String message) {
        mRecyclerViewPatient.setPullLoadMoreCompleted();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }

}
