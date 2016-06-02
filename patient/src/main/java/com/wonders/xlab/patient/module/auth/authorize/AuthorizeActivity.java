package com.wonders.xlab.patient.module.auth.authorize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.base.TextInputActivity;
import com.wonders.xlab.patient.module.auth.authorize.crop.CropActivity;
import com.wonders.xlab.patient.module.auth.authorize.di.AuthorizeModule;
import com.wonders.xlab.patient.module.auth.authorize.di.DaggerAuthorizeComponent;
import com.wonders.xlab.patient.module.auth.authorize.guide.AuthorizeGuideActivity;
import com.wonders.xlab.patient.mvp.presenter.AuthorizePresenterContract;
import com.wonders.xlab.patient.util.ImageViewManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class AuthorizeActivity extends AppbarActivity implements AuthorizePresenterContract.ViewListener {

    private final int REQUEST_CODE_NAME = 1236;
    private final int REQUEST_CODE_ID = 1237;
    private final int REQUEST_CODE_ID_PIC = 1238;
    private final int REQUEST_CROP_IMAGE = 1239;

    @Bind(R.id.tv_authorize_name)
    TextView mTvAuthorizeName;
    @Bind(R.id.rl_authorize_name)
    RelativeLayout mRlAuthorizeName;
    @Bind(R.id.tv_authorize_id)
    TextView mTvAuthorizeId;
    @Bind(R.id.rl_authorize_id)
    RelativeLayout mRlAuthorizeId;
    @Bind(R.id.iv_authorize_add_pic)
    ImageView mIvAuthorizeAddPic;

    private List<String> mTmpImageFilePath = new ArrayList<>();

    private AuthorizePresenterContract.Actions mAuthorizePresenter;
    /**
     * 保存选择的图片
     */
    private File mPickedIdPicFile;

    @Override
    public int getContentLayout() {
        return R.layout.authorize_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mAuthorizePresenter = DaggerAuthorizeComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .authorizeModule(new AuthorizeModule(this))
                .build()
                .getAuthorizePresenter();
        addPresenter(mAuthorizePresenter);
    }

    @OnClick(R.id.tv_authorize_example)
    public void showGuide() {
        startActivity(new Intent(this, AuthorizeGuideActivity.class));
    }

    @OnClick(R.id.rl_authorize_name)
    public void editName() {
        Intent intent = new Intent(this, TextInputActivity.class);
        Bundle data = new Bundle();
        data.putString(TextInputActivity.EXTRA_TITLE, "姓名");
        data.putString(TextInputActivity.EXTRA_HINT, "请输入姓名");
        data.putString(TextInputActivity.EXTRA_DEFAULT_VALUE, mTvAuthorizeName.getText().toString());
        intent.putExtras(data);
        startActivityForResult(intent, REQUEST_CODE_NAME);
    }

    @OnClick(R.id.rl_authorize_id)
    public void editId() {
        Intent intent = new Intent(this, TextInputActivity.class);
        Bundle data = new Bundle();
        data.putString(TextInputActivity.EXTRA_TITLE, "身份证号码");
        data.putString(TextInputActivity.EXTRA_HINT, "请输入身份证号码");
        data.putString(TextInputActivity.EXTRA_DEFAULT_VALUE, mTvAuthorizeId.getText().toString());
        intent.putExtras(data);
        startActivityForResult(intent, REQUEST_CODE_ID);
    }

    @OnClick(R.id.iv_authorize_add_pic)
    public void choosePic() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        startActivityForResult(intent, REQUEST_CODE_ID_PIC);
    }

    @OnClick(R.id.btn_authorize_submit)
    public void submit() {
        String name = mTvAuthorizeName.getText().toString();
        String idNo = mTvAuthorizeId.getText().toString();

        mAuthorizePresenter.authorize(AIManager.getInstance().getPatientId(), name, idNo, mPickedIdPicFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_CODE_NAME:
                    mTvAuthorizeName.setText(data.getStringExtra(TextInputActivity.EXTRA_RESULT));
                    break;
                case REQUEST_CODE_ID:
                    mTvAuthorizeId.setText(data.getStringExtra(TextInputActivity.EXTRA_RESULT));
                    break;
                case REQUEST_CODE_ID_PIC:
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    if (null == photos || photos.size() <= 0) {
                        return;
                    }

                    String path = photos.get(0);

                    Intent intent = new Intent(AuthorizeActivity.this, CropActivity.class);
                    intent.putExtra(CropActivity.EXTRA_IMAGE_PATH, path);
                    startActivityForResult(intent, REQUEST_CROP_IMAGE);

                    /*final Uri uri = Uri.fromFile(new File(path));
                    ImageViewManager.setImageViewWithUri(this, mIvAuthorizeAddPic, uri, ImageViewManager.PLACE_HOLDER_EMPTY);

                    Observable.from(photos)
                            .subscribeOn(Schedulers.io())
                            .map(new Func1<String, File>() {
                                @Override
                                public File call(String s) {
                                    return new File(s);
                                }
                            })
                            .filter(new Func1<File, Boolean>() {
                                @Override
                                public Boolean call(File file) {
                                    return null != file;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<File>() {
                                CropView cropView = new CropView(AuthorizeActivity.this);
                                final List<File> fileList = new ArrayList<>();

                                @Override
                                public void onCompleted() {
                                    mPickedIdPicFile = fileList.get(0);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    showShortToast(e.getMessage());
                                }

                                @Override
                                public void onNext(File file) {
                                    *//*int i = FileUtil.getFileSizeInM(file) / 2;
                                    if (i <= 0) {
                                        i = 1;
                                    }

                                    cropView.extensions()
                                            .load(uri);
                                    cropView.extensions()
                                            .crop()
                                            .quality(100 / i)
                                            .format(Bitmap.CompressFormat.JPEG)
                                            .into(file);*//*
                                    fileList.add(file);
                                }
                            });*/
                    break;
                case REQUEST_CROP_IMAGE:
                    String imageFilePath = data.getStringExtra(CropActivity.RESULT_IMAGE_PATH);

                    mTmpImageFilePath.add(imageFilePath);

                    mPickedIdPicFile = new File(imageFilePath);
                    Uri uri = Uri.fromFile(mPickedIdPicFile);
                    ImageViewManager.setImageViewWithUri(this, mIvAuthorizeAddPic, uri, ImageViewManager.PLACE_HOLDER_EMPTY);
                    break;
            }
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_save, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_save:
//                String name = mTvAuthorizeName.getText().toString();
//                String idNo = mTvAuthorizeId.getText().toString();
//
//                mAuthorizePresenter.authorize(AIManager.getInstance().getPatientId(), name, idNo, mPickedIdPicFile);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void authorizeSuccess(String message) {
        clearTmpImageFile();

        showShortToast(message);
        mTvAuthorizeName.setText("");
        mTvAuthorizeId.setText("");
        ImageViewManager.setImageViewWithDrawableId(this, mIvAuthorizeAddPic, R.drawable.ic_group_member_add, ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    private void clearTmpImageFile() {
        Observable.from(mTmpImageFilePath)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(getCacheDir(), s);
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file != null && file.exists();
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        file.delete();
                    }
                });
    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", "正在上传，请稍候...", null);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmptyView(String message) {
        showShortToast(message);
    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearTmpImageFile();
    }
}
