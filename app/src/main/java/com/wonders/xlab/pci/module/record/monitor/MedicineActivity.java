package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicineActivity extends AppbarActivity {

    @Bind(R.id.rv_medicine)
    RecyclerView mRvMedicine;

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
    }

}
