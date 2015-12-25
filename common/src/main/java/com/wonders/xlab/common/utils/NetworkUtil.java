package com.wonders.xlab.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by hua on 15/12/25.
 */
public class NetworkUtil {
    public static boolean hasNetwork(Context context) {
        if (context != null) {
            ConnectivityManager var1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo var2 = var1.getActiveNetworkInfo();
            return var2 != null && var2.isAvailable();
        } else {
            return false;
        }
    }

    public static boolean isWifiConnection(Context context) {
        return "WIFI".equals(getNetworkType(context));
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager var1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo var2 = var1.getActiveNetworkInfo();
        if (var2 != null && var2.isAvailable()) {
            int var3 = var2.getType();
            if (var3 == 1) {
                return "WIFI";
            } else {
                TelephonyManager var4 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                switch (var4.getNetworkType()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "unkonw network";
                }
            }
        } else {
            return "no network";
        }
    }
}
