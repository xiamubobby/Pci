package com.wonders.xlab.pci.module;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.wonders.xlab.common.utils.StreamUtil;
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.mydoctor.MyDoctorActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;

/**
 * Created by hua on 16/1/26.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {
    private Context mContext;

    private MainActivity activity;

    @Before
    public void init() {
        mContext = RuntimeEnvironment.application;

        activity = Robolectric.buildActivity(MainActivity.class).create().get();

    }

    private void prepareHttpResponse() {
        try {
            String responseJson = StreamUtil.InputStreamToString(mContext.getAssets().open("11"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyTitle() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        String title = toolbar.getTitle().toString();

        String label = activity.getResources().getString(R.string.app_name);
        Assert.assertEquals("the title is not correct", title, label);
    }

    @Test
    public void goToMyDoctorActivity() {
        Intent intent = new Intent(activity.getApplicationContext(), MyDoctorActivity.class);

    }
}
