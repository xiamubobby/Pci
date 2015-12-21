package com.wonders.xlab.pci.module.record.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.application.ToastManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.adapter.ReportDetailRVAdapter;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.rxbus.ReportDetailBus;
import com.wonders.xlab.pci.mvn.model.ReportDetailModel;
import com.wonders.xlab.pci.mvn.model.UploadPicModel;
import com.wonders.xlab.pci.mvn.view.report.ReportDetailView;
import com.wonders.xlab.pci.mvn.view.UploadPicView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ReportDetailActivity extends AppbarActivity implements ReportDetailView, UploadPicView {
    private final int REQUEST_CODE = 11231;
    public final static String EXTRA_TITLE = "extra_title";

    @Bind(R.id.rv_report_detail)
    RecyclerView mRvReportDetail;
    @Bind(R.id.fab_report_detail)
    FloatingActionButton mFabReportDetail;

    private ReportDetailRVAdapter mRvAdapter;

    private ReportDetailModel detailModel;

    private UploadPicModel mUploadPicModel;

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
        mUploadPicModel = new UploadPicModel(this);
        addModel(detailModel);
        addModel(mUploadPicModel);

        Intent intent = getIntent();
        if (intent == null) {
            paramError();
            return;
        }

        setTitle(intent.getStringExtra(EXTRA_TITLE));

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
        detailModel.getReportDetails(AIManager.getInstance(this).getUserId());
    }

    private Subscriber subscription;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() > 0) {
                    final List<File> fileList = new ArrayList<>();
                    subscription = new Subscriber<File>() {
                        @Override
                        public void onCompleted() {

                            boolean canUpload = true;
                            for (File file : fileList) {
                                if (file.length() / 1024 / 1024 > 10) {
                                    Toast.makeText(ReportDetailActivity.this, file.getName() + "大小超过10M，请选择小一点的图片!", Toast.LENGTH_LONG).show();
                                    canUpload = false;
                                }
                            }
                            if (canUpload) {
                                mUploadPicModel.upload(AIManager.getInstance(ReportDetailActivity.this).getUserId(), fileList);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(File o) {
                            fileList.add(o);
                        }

                    };
                    Observable.from(photos)
                            .subscribeOn(Schedulers.io())
                            .map(new Func1<String, File>() {
                                @Override
                                public File call(String s) {
                                    return new File(s);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(subscription);

                  /*  for (String photoUrl : photos) {

                        File portraitFile = new File(photoUrl);
                        Uri uri = Uri.fromFile(portraitFile);

                        //TODO upload to server
//                        uploadPortrait(portraitFile);
                    }*/

                }
            }
        }
    }

    private void setupRxBus() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
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
    public void getReportsFailed(String message) {
        Snackbar.make(mRvReportDetail, message, Snackbar.LENGTH_SHORT).show();
    }

    private ProgressDialog dialog;

    @Override
    public void uploading() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在上传...");
        }
        dialog.show();
    }

    @Override
    public void uploadPicsSuccess() {
        if (dialog != null) {
            dialog.dismiss();
        }
        Snackbar.make(mRvReportDetail, "上传成功！", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void uploadPicsFailed(String message) {
        if (dialog != null) {
            dialog.dismiss();
        }
        Snackbar.make(mRvReportDetail, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        ButterKnife.unbind(this);
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

}
