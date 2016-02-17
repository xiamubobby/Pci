package im.hua.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import xlab.wonders.com.common.BuildConfig;

/**
 * Created by hua on 16/1/26.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MD5UtilTest {
    @Test
    public void encrypt() {
        String md5Result = "e10adc3949ba59abbe56e057f20f883e";//md5 result of 123456
        String md5Str = "123456";

        MD5Util md5Util = new MD5Util();

        Assert.assertEquals("the encrypt algorithm is not right", md5Result, md5Util.encrypt(md5Str));
    }
}
