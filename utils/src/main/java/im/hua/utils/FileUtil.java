package im.hua.utils;

import java.io.File;

/**
 * Created by hua on 16/5/26.
 */
public class FileUtil {

    public static int getFileSizeInM(File file) {
        if (null == file) {
            return 0;
        }
        return (int) (file.length() / 1024 / 1024);
    }
}
