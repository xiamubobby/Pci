package com.wonders.xlab.pci.module.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.record.HealthPlanActivity;
import com.wonders.xlab.pci.module.record.monitor.HealthDataActivity;
import com.wonders.xlab.pci.module.record.report.ReportDetailActivity;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.iv_home_top_holder)
    ImageView mIvHomeTopHolder;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this)
                .load(R.drawable.home_top_holder)
                .crossFade()
                .into(mIvHomeTopHolder);
    }

    @OnClick(R.id.tv_new_home_daily_record)
    public void onDailyRecordClick() {
        startActivity(new Intent(getActivity(), DailyTaskActivity.class));
    }

    @OnClick(R.id.tv_new_home_upload_pic)
    public void onUploadPicClick() {
        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
        intent.putExtra(ReportDetailActivity.EXTRA_TITLE, "病历报告");
        startActivity(intent);
    }

    @OnClick(R.id.tv_new_home_health_advise)
    public void onHealthAdviseClick() {
        startActivity(new Intent(getActivity(), HealthPlanActivity.class));
    }

    @OnClick(R.id.tv_new_home_health_data)
    public void onHealthDataClick() {
        startActivity(new Intent(getActivity(), HealthDataActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
