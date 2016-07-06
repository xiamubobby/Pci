package com.wonders.xlab.patient.module.me.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.DaggerHealthRecordComponent;
import com.wonders.xlab.patient.module.healthrecord.HealthRecordModule;
import com.wonders.xlab.patient.module.me.doctor.di.DaggerDoctorListComponent;
import com.wonders.xlab.patient.module.me.doctor.di.DoctorListComponent;
import com.wonders.xlab.patient.module.me.doctor.di.DoctorListModule;
import com.wonders.xlab.patient.module.scan.ScannerActivity;
import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenter;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by xiamubobby on 16/7/6.
 */

public class MyDoctorActivity extends AppbarActivity implements DoctorListPresenterContract.ViewListener{

    private DoctorListPresenter doctorListPresenter;

    @Bind(R.id.rv_doctor_list)
    CommonRecyclerView commonRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.doctor_list_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.me_my_doctor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnNavigationClickListener(new OnNavigationClickListener() {
            @Override
            public boolean onNavigationClick() {
                finish();
                return true;
            }
        });
        getToolbar().inflateMenu(R.menu.scan_button);
        doctorListPresenter = DaggerDoctorListComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .doctorListModule(new DoctorListModule(this))
                .build()
                .getDoctorListPresenter();
        ButterKnife.bind(this);
//        getToolbar().getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        })
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_button, menu);
        return true;
    }

    public void doScan(MenuItem item) {
        startActivity(new Intent(this, ScannerActivity.class));
    }

    @Override
    public void showDoctorList(List<DoctorListEntity.DoctorPatientRelationDoctorList.DoctorPatientRelationDoctor> list) {

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
    public void showToast(String message) {

    }

    @Override
    public void hideLoading() {

    }
}
