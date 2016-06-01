package com.wonders.xlab.patient.module.me.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.service.XEMChatService;

import im.hua.utils.NotifyUtil;

/**
 * Created by hua on 15/11/16.
 */
public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences preferences;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }

    @Override
    public void onStart() {
        super.onStart();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initMeasureDefaultSummary(preferences);

        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.setting_pref_key_persistent_notification))) {
            boolean stayNotification = sharedPreferences.getBoolean(getResources().getString(R.string.setting_pref_key_persistent_notification), true);
            if (stayNotification) {
                getActivity().startService(new Intent(getActivity(), XEMChatService.class));
            } else {
                NotifyUtil.cancel(getActivity(), Constant.NOTIFY_ID_PERSISTENT);
            }
        } else if (key.equals(getResources().getString(R.string.setting_pref_key_measure_default))) {
            initMeasureDefaultSummary(sharedPreferences);
        }
    }

    private void initMeasureDefaultSummary(SharedPreferences sharedPreferences) {
        boolean measureDefault = sharedPreferences.getBoolean(getResources().getString(R.string.setting_pref_key_measure_default), true);
        String summary = getResources().getString(R.string.setting_summary_off_measure);
        if (measureDefault) {
            if (sharedPreferences.getBoolean(getResources().getString(R.string.setting_pref_key_use_equipment), true)) {
                summary = getResources().getString(R.string.setting_summary_on_measure_device);
            } else {
                summary = getResources().getString(R.string.setting_summary_on_measure_manual);
            }
        }
        SwitchPreference preference = (SwitchPreference) getPreferenceManager().findPreference(getResources().getString(R.string.setting_pref_key_measure_default));
        preference.setSummary(summary);
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
