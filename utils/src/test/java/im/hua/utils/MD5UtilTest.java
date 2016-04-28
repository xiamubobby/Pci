package im.hua.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hua on 16/4/28.
 */
public class MD5UtilTest {

    @Test
    public void testEncrypt() throws InterruptedException {
        MD5Util md5Util = new MD5Util();
        Assert.assertEquals(md5Util.encrypt("123456"), "e10adc3949ba59abbe56e057f20f883e");
    }
}