package com.wonders.xlab.patient.module.medicineremind.edit.medicine.add;

import android.os.Bundle;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.Bind;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by WZH on 16/5/5.
 */
public class AddMedicineActivity extends AppbarActivity {

    @Bind(R.id.recycler_view_medicine_add_list)
    CommonRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentLayout() {
        return R.layout.medicine_add_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "添加药品名称";
    }
}
