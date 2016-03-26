package com.wonders.xlab.pci.module.mydoctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.mydoctor.mvn.DoctorInfoEntity;
import com.wonders.xlab.pci.module.mydoctor.mvn.MyDoctorModel;
import com.wonders.xlab.pci.module.mydoctor.mvn.MyDoctorView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyDoctorFragment extends BaseFragment implements MyDoctorView {

    @Bind(R.id.rv_my_doctor)
    RecyclerView mRvMyDoctor;
    @Bind(R.id.container)
    RelativeLayout mContainer;

    private MyDoctorRVAdapter mDoctorRVAdapter;
    private MyDoctorModel mMyDoctorModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_doctor, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyDoctorModel = new MyDoctorModel(this);
        addModel(mMyDoctorModel);

        mRvMyDoctor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvMyDoctor.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 12));

        mMyDoctorModel.getDoctorList(AIManager.getInstance(getActivity()).getUserId(), AIManager.getInstance(getActivity()).getUserTel());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDoctorList(List<DoctorInfoEntity> doctorInfoList) {
        if (mDoctorRVAdapter == null) {
            mDoctorRVAdapter = new MyDoctorRVAdapter();
            mRvMyDoctor.setAdapter(mDoctorRVAdapter);
        }
        mDoctorRVAdapter.setDatas(doctorInfoList);
    }

    @Override
    public void showError(String message) {
        showSnackbar(mContainer, message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
