package im.hua.utils;

import android.util.Log;

/**
 * Created by hua on 16/3/17.
 */
public class LogUtil {
    public static void debug(String message) {
        Log.d("LogUtil", message);
    }

    public static void warn(String message) {
        Log.w("LogUtil", message);
    }

    public static void error(String message) {
        Log.e("LogUtil", message);
    }
}
