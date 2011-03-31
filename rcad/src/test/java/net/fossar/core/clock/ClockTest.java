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
