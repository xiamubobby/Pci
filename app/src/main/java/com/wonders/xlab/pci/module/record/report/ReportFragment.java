package com.wonders.xlab.pci.module.record.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends BaseFragment {

    @Bind(R.id.tv_report_after)
    TextView mTvReportAfter;
    @Bind(R.id.tv_report_now)
    TextView mTvReportNow;
    @Bind(R.id.tv_report_before)
    TextView mTvReportBefore;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewClick(mTvReportAfter);
        setupViewClick(mTvReportNow);
        setupViewClick(mTvReportBefore);
    }

    private void setupViewClick(final TextView textView) {
        RxView.clicks(textView)
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                        intent.putExtra(ReportDetailActivity.EXTRA_TITLE, textView.getText().toString());
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
