package com.wonders.xlab.patient.module.me.about;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/6/7.
 */
public class AboutUsActivity extends AppbarActivity {
    @Bind(R.id.app_version)
    TextView mAppVersion;

    @Override
    public int getContentLayout() {
        return R.layout.about_us_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);

            mAppVersion.setText(getString(R.string.about_us_app_version, info.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
