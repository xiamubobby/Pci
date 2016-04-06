package com.wonders.xlab.patient.module.main.me.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;

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

//        boolean useEquipment = SPManager.get(getActivity()).getBoolean(getString(R.string.setting_pref_key_use_equipment), false);
//
//        SwitchPreference preference = (SwitchPreference) findPreference(getResources().getString(R.string.setting_pref_key_use_equipment));
//        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.d("SettingFragment", "newValue:" + newValue);
//                return false;
//            }
//        });
//        preference.setSummary(useEquipment?"默认使用全程健康设备测量":"默认手动录入健康数据");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_setting));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_setting));
    }

}
