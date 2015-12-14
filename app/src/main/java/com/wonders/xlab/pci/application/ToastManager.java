package com.wonders.xlab.pci.application;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hua on 15/12/14.
 */
public class ToastManager {
    public static void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
