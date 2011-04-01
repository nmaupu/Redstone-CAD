/*
 * RCad is a software to help manipulating minecraft's redstone.
 * Copyright (C) 2011. mathieu_dot_grenonville_at_gmail_dot_com - nmaupu_at_gmail_dot_com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fossar.core.clock;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.fossar.model.core.clock.Clock;
import net.fossar.model.core.clock.Time;

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
