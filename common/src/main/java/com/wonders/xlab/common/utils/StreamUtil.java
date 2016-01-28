package com.wonders.xlab.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hua on 16/1/12.
 */
public class StreamUtil {
    final static int BUFFER_SIZE = 4096;

    public static String InputStreamToString(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];

        int count;
        try {
            while ((count = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                outputStream.write(data, 0, count);
            }
            data = null;
            return new String(outputStream.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
