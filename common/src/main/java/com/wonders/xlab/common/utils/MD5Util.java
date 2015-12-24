package com.wonders.xlab.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hua on 15/12/24.
 */
public class MD5Util {
    public String encrypt(String source) {
        MessageDigest msgDigest;
        String result = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(source.getBytes());

            byte[] digest = msgDigest.digest();
            StringBuffer md5Hash = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String h = Integer.toHexString(0xFF & digest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                md5Hash.append(h);
            }
            result = md5Hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
