package com.wonders.xlab.patient.module.scan;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.wonders.xlab.patient.R;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.Bind;
import im.hua.library.base.BaseActivity;
import im.hua.utils.DensityUtil;

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
        Camera.Size biggest = null;
        Camera.Size suitable = null;
        Camera.Size candidate = null;
        for (Camera.Size size : sizes) {
            if (size.width > DensityUtil.dp2px(this, 200) && size.height > DensityUtil.dp2px(this, 200) && (suitable == null || (suitable.width > size.width && suitable.height > size.height)))
                suitable = size;
            if (biggest == null || (biggest.width < size.width))
                biggest = size;
        }
        if (suitable != null)
            candidate = suitable;
        else
            candidate = biggest;
        if (candidate == null) return;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) theViewScope.getLayoutParams();
        params.width = candidate.height;
        params.height = candidate.width;
        theViewScope.setLayoutParams(params);
        parameters.setPreviewSize(candidate.width, candidate.height);
        parameters.setPreviewFormat(ImageFormat.NV21);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
        final int[] i = {1};
        theViewScope.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    camera.setPreviewDisplay(theViewScope.getHolder());
                    camera.setPreviewCallback(new Camera.PreviewCallback() {
                        @Override
                        public void onPreviewFrame(byte[] data, Camera camera) {
                            PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(
                                    data,
                                    camera.getParameters().getPreviewSize().width,
                                    camera.getParameters().getPreviewSize().height,
                                    0,
                                    0,
                                    camera.getParameters().getPreviewSize().width,
                                    camera.getParameters().getPreviewSize().height,
                                    true
                            );
                            new DecodeThread(planarYUVLuminanceSource).execute();
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

    class DecodeThread extends AsyncTask<Void, Void, String> {
        private final LuminanceSource planarYUVLuminanceSource;

        DecodeThread(PlanarYUVLuminanceSource planarYUVLuminanceSource) {
            this.planarYUVLuminanceSource = planarYUVLuminanceSource;
        }
        @Override
        protected String doInBackground(Void... params) {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(planarYUVLuminanceSource));
            Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(3);
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//            hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, listener);
            QRCodeReader qrCodeReader = new QRCodeReader();
//            qrCodeReader..setHints(hints);
            long start = System.currentTimeMillis();
            Result rawResult = null;
            rawResult = null;
            try {
                rawResult = qrCodeReader.decode(bitmap);
            } catch (Exception e) {
                Log.e("decode", "catched error");
            }
//                Bitmap bitmap1 = Bitmap.createBitmap(planarYUVLuminanceSource.getWidth(), planarYUVLuminanceSource.getHeight(), Bitmap.Config.ARGB_8888);
//                int[] pixels = new int[planarYUVLuminanceSource.getWidth() * planarYUVLuminanceSource.getHeight()];
//                bitmap1.setPixels(planarYUVLuminanceSource.getMatrix(), 0, planarYUVLuminanceSource.getWidth(), 0, planarYUVLuminanceSource.getWidth(), 0, planarYUVLuminanceSource.getHeight());
//                long end = System.currentTimeMillis();
//                Log.d("DecodeThread", "Decode use " + (end - start) + "ms");
                qrCodeReader.reset();
            return rawResult.getText();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(ScannerActivity.this, "", Toast.LENGTH_LONG).show();
        }
    }
}
