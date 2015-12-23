package com.wonders.xlab.pci.module.record.monitor;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSActivity extends AppbarActivity {
    @Bind(R.id.stl_bs_time_filter)
    SegmentTabLayout stlBsTimeFilter;
    @Bind(R.id.tv_bs_week)
    TextView tvBsWeek;
    @Bind(R.id.ry_bs_history)
    RecyclerView ryBsHistory;

    @Override
    public int getContentLayout() {
        return R.layout.activity_bs;
    }

    @Override
    public String getToolbarTitle() {
        return "血糖";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }
}
