package net.fossar.core.clock;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Ignore;

@Ignore
public class TimeTest extends TestCase {
    public void testGetTime() throws Exception {
        long l = Clock.currentTime();
        Time t = new Time();
        Assert.assertEquals(l, t.getTime());
    }

    public void testIsExpired() throws Exception {
        Time t = new Time();
        assertFalse("t is not expired", t.isExpired(10));
        Clock.next();
        assertTrue("t is not expired", t.isExpired(10));
    }
}
