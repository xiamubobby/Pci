package com.wonders.xlab.pci.module.record.monitor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.adapter.MedicineRVAdapter;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.MedicineModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.MedicineView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicineActivity extends AppbarActivity implements MedicineView {

    @Bind(R.id.rv_medicine)
    RecyclerView mRvMedicine;

    private MedicineRVAdapter mMedicineRVAdapter;

    private MedicineModel mMedicineModel;

    @Override
    public int getContentLayout() {
        return R.layout.activity_medicine;
    }

    @Override
    public String getToolbarTitle() {
        return "用药";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRvMedicine.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mMedicineModel = new MedicineModel(this);
        addModel(mMedicineModel);

        mMedicineModel.getMedicineRecords();
    }

    @Override
    public void showMedicineRecords(List<MedicineCategoryBean> categoryBeanList) {
        if (mMedicineRVAdapter == null) {
            mMedicineRVAdapter = new MedicineRVAdapter(new WeakReference<Context>(this),categoryBeanList);
            mRvMedicine.setAdapter(mMedicineRVAdapter);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
