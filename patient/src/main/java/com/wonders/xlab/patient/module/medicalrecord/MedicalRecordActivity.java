package com.wonders.xlab.patient.module.medicalrecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.medicalrecord.adapter.MedicalRecordRVAdapter;
import com.wonders.xlab.patient.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.patient.mvp.presenter.IUploadPicPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.MedicalRecordPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.UploadPicPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MedicalRecordActivity extends AppbarActivity implements MedicalRecordPresenter.MedicalRecordPresenterListener, UploadPicPresenter.UploadPicModelListener {

    public static final String EXTRA_PATIENT_ID = "patientId";

    private static final int REQUEST_CODE = 1123;

    private static final int REQUEST_PICTURES_CODE = 1124;

    @Bind(R.id.recycler_view_medical_record)
    PullLoadMoreRecyclerView mRecyclerView;
    private MedicalRecordRVAdapter mMedicalRecordRVAdapter;

    private MedicalRecordPresenter mMedicalRecordPresenter;

    private IUploadPicPresenter mUploadPicPresenter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.medical_record_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "就诊记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取患者就诊记录失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取患者就诊记录失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 5));
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId, true);
            }

            @Override
            public void onLoadMore() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId, false);
            }
        });

        mMedicalRecordPresenter = new MedicalRecordPresenter(this);
        mUploadPicPresenter = new UploadPicPresenter(this);
        addPresenter(mMedicalRecordPresenter);

        mRecyclerView.setRefreshing(true);
        mMedicalRecordPresenter.getMedicalRecordList(mPatientId, true);
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
                                    Toast.makeText(MedicalRecordActivity.this, file.getName() + "大小超过10M，请选择小一点的图片!", Toast.LENGTH_LONG).show();
                                    canUpload = false;
                                }
                            }
                            if (canUpload) {
                                mUploadPicPresenter.upload(AIManager.getInstance(MedicalRecordActivity.this).getPatientId(), fileList);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        dismissProgressDialog();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void showMedicalRecordList(List<MedicalRecordBean> beanList) {
        initMedicalRecordRVAdapter();
        mRecyclerView.setAdapter(mMedicalRecordRVAdapter);
        mMedicalRecordRVAdapter.setDatas(beanList);
    }

    private void initMedicalRecordRVAdapter() {
        if (null == mMedicalRecordRVAdapter) {
            mMedicalRecordRVAdapter = new MedicalRecordRVAdapter();
            mMedicalRecordRVAdapter.setOnPhotoClickListener(new MedicalRecordRVAdapter.OnPhotoClickListener() {
                @Override
                public void onPhotoClick(ArrayList<String> photoUrls, int selectedPosition) {
                    Intent intent = new Intent(MedicalRecordActivity.this, PhotoPagerActivity.class);
                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, selectedPosition);
                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoUrls);
                    intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, false); // default is true
                    startActivityForResult(intent, REQUEST_PICTURES_CODE);
                }
            });
        }
    }

    @Override
    public void appendMedicalRecordList(List<MedicalRecordBean> beanList) {
        initMedicalRecordRVAdapter();
        mRecyclerView.setAdapter(mMedicalRecordRVAdapter);
        mMedicalRecordRVAdapter.appendDatas(beanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        mRecyclerView.setPullLoadMoreCompleted();
        mRecyclerView.setRefreshing(false);
        dismissProgressDialog();
    }

    @Override
    public void uploading() {
        showProgressDialog("", "正在上传图片，请稍候...");
    }

    @Override
    public void uploadPicsSuccess(String message) {
        showShortToast(message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medical_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_medical_record_photo_upload:
                PhotoPickerIntent intent = new PhotoPickerIntent(MedicalRecordActivity.this);
                intent.setPhotoCount(9);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
