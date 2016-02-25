package com.wonders.xlab.common.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPManager {

    private static SharedPreferences mSP;
    private static SPManager spManager;

    private SPManager() {

    }
    public static SPManager get(Context context) {
        if (spManager == null) {
            synchronized (SPManager.class) {
                if (spManager == null) {
                    mSP = PreferenceManager.getDefaultSharedPreferences(context);
                    spManager = new SPManager();
                }
            }
        }
        return spManager;
    }

    public String getString(String key, String defaultValue) {

        if (mSP != null) {

            return mSP.getString(key, defaultValue);
        }

        return defaultValue;
    }

    public int getInt(String key, int defaultValue) {

        if (mSP != null) {

            return mSP.getInt(key, defaultValue);
        }

        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {

        if (mSP != null) {

            return mSP.getBoolean(key, defaultValue);
        }

        return defaultValue;
    }

    public long getLong(String key, long defaultValue) {

        if (mSP != null) {

            return mSP.getLong(key, defaultValue);
        }

        return defaultValue;
    }

    public void putString(String key, String value) {
        if (mSP != null) {
            mSP.edit().putString(key, value).apply();
        }
    }

    public void putInt(String key, int value) {
        if (mSP != null) {
            mSP.edit().putInt(key, value).apply();
        }
    }

    public void putBoolean(String key, boolean value) {
        if (mSP != null) {
            mSP.edit().putBoolean(key, value).apply();
        }
    }

    public void putLong(String key, long value) {
        if (mSP != null) {
            mSP.edit().putLong(key, value).apply();
        }
    }

    public void clear() {
        if (mSP != null) {
            mSP.edit().clear().apply();
        }
    }
}
