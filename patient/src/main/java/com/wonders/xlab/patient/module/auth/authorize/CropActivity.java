package com.wonders.xlab.patient.module.auth.authorize;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.lyft.android.scissors.CropView;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.FileUtil;
import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class CropActivity extends AppbarActivity {
    public final static String EXTRA_IMAGE_PATH = "imagePath";
    public final static String RESULT_IMAGE_PATH = "cropImagePath";

    @Bind(R.id.cropView)
    CropView mCropView;

    private File mCroppedFile;
    private int quality;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public int getContentLayout() {
        return R.layout.crop_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            finish();
            return;
        }
        String path = intent.getStringExtra(EXTRA_IMAGE_PATH);
        if (TextUtils.isEmpty(path)) {
            finish();
            return;
        }
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        quality = FileUtil.getFileSizeInM(file) / 2;
        if (quality <= 0) {
            quality = 1;
        } else if(quality >=100) {
            quality = 50;
        }

//        ImageViewManager.setImageViewWithUri(this,mCropView,uri,ImageViewManager.PLACE_HOLDER_EMPTY);
        mCropView.extensions()
                .load(uri);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onRatio();
    }

    public void onRatio() {
        final float oldRatio = mCropView.getImageRatio();

        // Since the animation needs to interpolate to the native
        // ratio, we need to get that instead of using 0
        float newRatio = 3.0f / 2;
        if (Float.compare(0, newRatio) == 0) {
            newRatio = mCropView.getImageRatio();
        }

        ObjectAnimator viewportRatioAnimator = ObjectAnimator.ofFloat(mCropView, "viewportRatio", oldRatio, newRatio)
                .setDuration(420);
        autoCancel(viewportRatioAnimator);
        viewportRatioAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    static void autoCancel(ObjectAnimator objectAnimator) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            objectAnimator.setAutoCancel(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                mCroppedFile = new File(getCacheDir(), "cropped" + System.currentTimeMillis() + ".jpg");

                Observable<Void> onSave = Observable.from(mCropView.extensions()
                        .crop()
                        .quality(100 / quality)
                        .format(JPEG)
                        .into(mCroppedFile))
                        .subscribeOn(io())
                        .observeOn(mainThread());

                subscriptions.add(onSave
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void nothing) {
                                String path = mCroppedFile.getPath();

                                Intent intent = new Intent();
                                intent.putExtra(RESULT_IMAGE_PATH, path);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }
}
