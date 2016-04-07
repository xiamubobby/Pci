package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.os.Bundle;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

import butterknife.Bind;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupManageActivity extends AppbarActivity {

    @Bind(R.id.recycler_view_group_manage)
    CommonRecyclerView mRecyclerView;

    @Override
    public int getContentLayout() {
        return R.layout.group_manage_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
