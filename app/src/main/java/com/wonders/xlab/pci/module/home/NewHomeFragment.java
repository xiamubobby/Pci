package com.wonders.xlab.pci.module.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.record.monitor.HealthDataActivity;
import com.wonders.xlab.pci.module.record.report.ReportDetailActivity;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends BaseFragment {

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);
        ButterKnife.bind(this, view);
        return view;
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