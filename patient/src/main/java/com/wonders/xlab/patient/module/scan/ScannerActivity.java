package com.wonders.xlab.patient.module.scan;

import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import com.wonders.xlab.patient.R;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import im.hua.library.base.BaseActivity;

/**
 * Created by xiamubobby on 16/7/5.
 */

public class ScannerActivity extends BaseActivity {

    SurfaceView theViewScope;
    Camera camera = null;

    final static int CAMERA_NATURAL_DEGREE = 90;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity);
        theViewScope = (SurfaceView) findViewById(R.id.textureView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            // TODO: 16/7/5 add hint for no camera
            return;
        if (camera == null) {
            try {
                camera = Camera.open();
            } catch (Exception e) {
                // TODO: 16/7/5 add hint for camera not available
            }
        }
        if (camera == null) return;
        camera.setDisplayOrientation(CAMERA_NATURAL_DEGREE);
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        Camera.Size candidate = null;
        for (Camera.Size size : sizes) {
            candidate = size;
            if (size.width < theViewScope.getWidth()) continue;
            if (size.height < theViewScope.getHeight()) continue;
            break;
        }
        if (candidate == null) return;
        parameters.setPreviewSize(candidate.width, candidate.height);
        parameters.setPreviewFormat(ImageFormat.NV21);
        camera.setParameters(parameters);
        final int[] i = {1};
        theViewScope.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    camera.setPreviewDisplay(theViewScope.getHolder());
                    camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                        @Override
                        public void onPreviewFrame(byte[] data, Camera camera) {
                            Log.d("atest", ""+i[0]++);
                            Log.d("adata", data.toString());
                        }
                    });
                } catch (IOException e) {
                    // TODO: 16/7/5 preview failure
                    e.printStackTrace();
                    return;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        camera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
