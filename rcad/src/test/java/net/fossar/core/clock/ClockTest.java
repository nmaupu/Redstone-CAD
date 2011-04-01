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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;
import net.fossar.model.core.clock.Clock;

import org.junit.Ignore;

@Ignore
public class ClockTest extends TestCase {

    public void testCurrentTime_at_start_returns_0() throws Exception {
        assertEquals(0, Clock.currentTime());
    }

    public void testNext_should_increment_of_period() throws Exception {
        assertEquals(0, Clock.currentTime());
        Clock.next();
        assertEquals(10, Clock.currentTime());
    }

    public void testNext_should_increment_in_multi_thread() throws Exception {

        final Lock lock = new ReentrantLock();
        
        for (int test = 0; test < 100; test++) {
            Runnable runnable = new Runnable() {
                public void run() {

                    for (int i = 0; i < 10000; i++) {
                        Clock.next();
                    }
                    lock.lock();
                    lock.unlock();

                }

            };
            runnable.run();
        }

    for (int test = 0; test < 10; test++) {
        Runnable runnable1 = new Runnable() {
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    Clock.currentTime();
                }
                lock.lock();
                lock.unlock();

            }
        };

        runnable1.run();
    }
        lock.lock();
        lock.unlock();


        assertEquals(100 * 10000 * 10, Clock.currentTime());

    }

}
