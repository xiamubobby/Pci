package com.wonders.xlab.common.utils;

import android.app.Activity;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by hua on 15/12/9.
 */
public class KeyboardUtil {
    public static void hide(Activity activity, IBinder windowToken) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
    }
}
