package com.wonders.xlab.patient.module.main.home.medicalrecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.main.home.medicalrecord.adapter.MedicalRecordRVAdapter;
import com.wonders.xlab.patient.module.main.home.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.patient.mvp.presenter.IUploadPicPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.MedicalRecordPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.UploadPicPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;
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
    CommonRecyclerView mRecyclerView;

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

        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 5));
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId, false);
            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId, true);
            }
        });

        mMedicalRecordPresenter = new MedicalRecordPresenter(this);
        mUploadPicPresenter = new UploadPicPresenter(this);
        addPresenter(mMedicalRecordPresenter);
        addPresenter(mUploadPicPresenter);

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
                                    showShortToast(file.getName() + "大小超过10M，请选择小一点的图片!");
                                    canUpload = false;
                                }
                            }
                            if (canUpload) {
                                final EditText editText = (EditText) LayoutInflater.from(MedicalRecordActivity.this).inflate(R.layout.simple_single_line_edit_text, null, false);
                                editText.setHint("请输入自传病历报告标题(最多200个字符)");
                                final AlertDialog alertDialog = new AlertDialog.Builder(MedicalRecordActivity.this)
                                        .setTitle("自传病历报告")
                                        .setPositiveButton("确定", null)
                                        .setNegativeButton("取消", null)
                                        .setView(editText).create();

                                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
                                        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String title = editText.getText().toString();
                                                if (!TextUtils.isEmpty(title)) {
                                                    KeyboardUtil.hide(MedicalRecordActivity.this);
                                                    mUploadPicPresenter.upload(AIManager.getInstance().getPatientId(), title, fileList);
                                                    alertDialog.dismiss();
                                                } else {
                                                    showShortToast("请输入标题");
                                                }
                                            }
                                        });
                                    }
                                });
                                editText.requestFocus();
                                alertDialog.show();
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
    public void showMedicalRecordList(List<MedicalRecordBean> beanList) {
        initMedicalRecordRVAdapter();
        mRecyclerView.setAdapter(mMedicalRecordRVAdapter);
        mMedicalRecordRVAdapter.setDatas(beanList);
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
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
        dismissProgressDialog();
    }

    @Override
    public void uploading() {
        showProgressDialog("", "正在上传图片，请稍候...");
    }

    @Override
    public void uploadPicsSuccess(String message) {
        showShortToast(message);
        mMedicalRecordPresenter.getMedicalRecordList(mPatientId, true);
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_medical_record));
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_medical_record));
        MobclickAgent.onPause(this);
    }
}
