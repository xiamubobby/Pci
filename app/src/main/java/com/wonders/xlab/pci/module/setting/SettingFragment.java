package com.wonders.xlab.pci.module.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wonders.xlab.pci.R;

/**
 * Created by hua on 15/11/16.
 */
public class SettingFragment extends PreferenceFragment {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

    }
}
