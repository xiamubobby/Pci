package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.data.presenter.impl.MedicalRecordPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.adapter.MedicalRecordRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.bean.MedicalRecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;
import me.iwf.photopicker.PhotoPagerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalRecordFragment extends BaseFragment implements MedicalRecordPresenter.MedicalRecordPresenterListener {
    public static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    private static final int REQUEST_CODE = 1123;

    @Bind(R.id.recycler_view_medical_record)
    CommonRecyclerView mRecyclerView;

    private MedicalRecordRVAdapter mMedicalRecordRVAdapter;

    private MedicalRecordPresenter mMedicalRecordPresenter;

    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    public static MedicalRecordFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID,patientId);
        MedicalRecordFragment fragment = new MedicalRecordFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.medical_record_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
            }
        });
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 5));

        mMedicalRecordPresenter = new MedicalRecordPresenter(this);
        addPresenter(mMedicalRecordPresenter);

        mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMedicalRecordRVAdapter = null;
        ButterKnife.unbind(this);
    }

    @Override
    public void showMedicalRecordList(List<MedicalRecordBean> beanList) {
        if (null == mMedicalRecordRVAdapter) {
            mMedicalRecordRVAdapter = new MedicalRecordRVAdapter();
            mMedicalRecordRVAdapter.setOnPhotoClickListener(new MedicalRecordRVAdapter.OnPhotoClickListener() {
                @Override
                public void onPhotoClick(ArrayList<String> photoUrls, int selectedPosition) {
                    Intent intent = new Intent(getActivity(), PhotoPagerActivity.class);
                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, selectedPosition);
                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoUrls);
                    intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, false); // default is true
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
        mMedicalRecordRVAdapter.setDatas(beanList);

        mRecyclerView.setAdapter(mMedicalRecordRVAdapter);
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
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}
