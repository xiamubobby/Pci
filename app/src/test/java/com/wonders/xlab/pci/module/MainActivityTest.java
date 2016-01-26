package com.wonders.xlab.pci.module;


import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.setting.SettingActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by hua on 16/1/26.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private SettingActivity activity;

    @Before
    public void init() {
        activity = Robolectric.buildActivity(SettingActivity.class).create().get();

    }

    @Test
    public void verifyTitle() {
        String title = activity.getToolbarTitle();

        String label = activity.getString(R.string.label_setting);
        Assert.assertEquals("the title is not correct", title, label);
    }

    @Test
    public void showMessageList() {
        Assert.assertEquals("not equal", 1, 1);
    }
}
