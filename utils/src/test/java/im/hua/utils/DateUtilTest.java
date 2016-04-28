package im.hua.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by hua on 16/4/28.
 */
public class DateUtilTest {

    @Test
    public void testGetStartTimeInMillOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Assert.assertEquals(calendar.getTimeInMillis(), DateUtil.getStartTimeInMillOfToday());
    }

    @Test
    public void testGetEndTimeInMillOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        Assert.assertEquals(calendar.getTimeInMillis(), DateUtil.getEndTimeInMillOfToday());
    }
}
