package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.monitor.adapter.MedicineRVAdapter;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.mvn.model.MedicineModel;
import com.wonders.xlab.pci.module.record.monitor.mvn.view.MedicineView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicineActivity extends AppbarActivity implements MedicineView {

    @Bind(R.id.rv_medicine)
    RecyclerView mRvMedicine;
    @Bind(R.id.refresh_medicine)
    SwipeRefreshLayout mRefreshMedicine;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

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

        mRefreshMedicine.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicineModel.getMedicineRecords(AIManager.getInstance(MedicineActivity.this).getUserId());
            }
        });
        mRvMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mMedicineModel = new MedicineModel(this);
        addModel(mMedicineModel);

        mMedicineModel.getMedicineRecords(AIManager.getInstance(this).getUserId());
    }

    @Override
    public void showMedicineRecords(List<MedicineCategoryBean> categoryBeanList) {
        if (mMedicineRVAdapter == null) {
            mMedicineRVAdapter = new MedicineRVAdapter(categoryBeanList);
            mRvMedicine.setAdapter(mMedicineRVAdapter);
        }
        mMedicineRVAdapter.setDatas(categoryBeanList);
    }

    @Override
    public void onFailed(String message) {
        showSnackbar(mCoordinator, message, true);
    }

    @Override
    public void showLoading() {
        mRefreshMedicine.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshMedicine != null) {
                    mRefreshMedicine.setRefreshing(true);
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        mRefreshMedicine.post(new Runnable() {
            @Override
            public void run() {
                if (mRefreshMedicine != null) {
                    mRefreshMedicine.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
