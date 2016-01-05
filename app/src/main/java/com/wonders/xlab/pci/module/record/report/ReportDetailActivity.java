package com.wonders.xlab.pci.module.record.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.LoadMoreRecyclerView;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.application.ToastManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.record.report.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.report.mvn.model.ReportDetailModel;
import com.wonders.xlab.pci.module.record.report.mvn.view.ReportDetailView;
import com.wonders.xlab.pci.module.record.otto.ReportDetailBus;
import com.wonders.xlab.pci.module.base.mvn.model.UploadPicModel;
import com.wonders.xlab.pci.module.base.mvn.view.UploadPicView;

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
    LoadMoreRecyclerView mRvReportDetail;
    @Bind(R.id.fab_report_detail)
    FloatingActionButton mFabReportDetail;

    private ReportDetailRVAdapter mRvAdapter;

    private ReportDetailModel detailModel;

    private UploadPicModel mUploadPicModel;

    private CompositeSubscription mSubscription;

    private int schedule;//0:术前 1:术中 2:术后

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

        detailModel = new ReportDetailModel(this);
        mUploadPicModel = new UploadPicModel(this);
        addModel(detailModel);
        addModel(mUploadPicModel);

        Intent intent = getIntent();
        if (intent == null) {
            paramError();
            return;
        }

        String title = intent.getStringExtra(EXTRA_TITLE);
        setTitle(title);

        switch (title) {
            case "术前病历":
                schedule = 0;
                break;
            case "术中病历":
                schedule = 1;
                break;
            case "术后病历":
                schedule = 2;
                break;
            default:
                schedule = 0;
        }

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
        mRvReportDetail.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreToBottom() {
                detailModel.getReportDetails(AIManager.getInstance(ReportDetailActivity.this).getUserId(), schedule);
            }

            @Override
            public void loadMoreToTop() {

            }
        });
        detailModel.getReportDetails(AIManager.getInstance(this).getUserId(), schedule);
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
        } else {
            mRvAdapter.clear();
        }
        mRvAdapter.setDatas(detailBeanList);
    }

    @Override
    public void appendReportList(List<ReportDetailBean> detailBeanList) {
        if (mRvAdapter == null) {
            mRvAdapter = new ReportDetailRVAdapter(new WeakReference<Context>(this));
            mRvReportDetail.setAdapter(mRvAdapter);
        }
        mRvAdapter.appendDatas(detailBeanList);
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
        Snackbar.make(mRvReportDetail, "上传成功！我们将于1个工作日后整理好资料并同步给您。请耐心等待。", Snackbar.LENGTH_INDEFINITE)
                .setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("病历报告");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("病历报告");
        MobclickAgent.onPause(this);
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
