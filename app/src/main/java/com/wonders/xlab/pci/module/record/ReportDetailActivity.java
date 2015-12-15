package com.wonders.xlab.pci.module.record;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.application.ToastManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.adapter.ReportDetailRVAdapter;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.rxbus.ReportDetailBus;
import com.wonders.xlab.pci.mvn.model.ReportDetailModel;
import com.wonders.xlab.pci.mvn.view.ReportDetailView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class ReportDetailActivity extends AppbarActivity implements ReportDetailView {
    private final int REQUEST_CODE = 11231;

    @Bind(R.id.rv_report_detail)
    RecyclerView mRvReportDetail;
    @Bind(R.id.fab_report_detail)
    FloatingActionButton mFabReportDetail;

    private ReportDetailRVAdapter mRvAdapter;

    private ReportDetailModel detailModel;

    private CompositeSubscription mSubscription;

    @Override
    public int getContentLayout() {
        return R.layout.activity_report_detail;
    }

    @Override
    public String getToolbarTitle() {
        return "病例报告";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

//        setupRxBus();

        detailModel = new ReportDetailModel(this);
        addModel(detailModel);

        Intent intent = getIntent();
        if (intent == null) {
            paramError();
            return;
        }
        Bundle data = intent.getExtras();
        if (data == null) {
            paramError();
            return;
        }

        setTitle(data.getString("title", "病例报告"));

        RxView.clicks(mFabReportDetail)
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        PhotoPickerIntent intent = new PhotoPickerIntent(ReportDetailActivity.this);
                        intent.setPhotoCount(9);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

        mRvReportDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        detailModel.getReportDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() > 0) {
                    for (String photoUrl : photos) {

                        File portraitFile = new File(photoUrl);
                        Uri uri = Uri.fromFile(portraitFile);

                        //TODO upload to server
//                        uploadPortrait(portraitFile);
                    }

                }
            }
        }
    }

    private void setupRxBus() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getRxBusSingleton().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof ReportDetailBus) {
                    ReportDetailBus reportDetailBus = (ReportDetailBus) o;
                    ToastManager.showToast(ReportDetailActivity.this, reportDetailBus.getPicName());
                }
            }
        }));
    }

    private void paramError() {
        ToastManager.showToast(this, "参数错误，请重试！");
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showReportList(List<ReportDetailBean> detailBeanList) {
        if (mRvAdapter == null) {
            mRvAdapter = new ReportDetailRVAdapter(new WeakReference<Context>(this));
            mRvReportDetail.setAdapter(mRvAdapter);
        }
        mRvAdapter.setDatas(detailBeanList);
    }

    @Override
    public void appendReportList(List<ReportDetailBean> detailBeanList) {
        if (mRvAdapter == null) {
            mRvAdapter = new ReportDetailRVAdapter(new WeakReference<Context>(this));
            mRvReportDetail.setAdapter(mRvAdapter);
        } else {
            mRvAdapter.clear();
        }
        mRvAdapter.setDatas(detailBeanList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
