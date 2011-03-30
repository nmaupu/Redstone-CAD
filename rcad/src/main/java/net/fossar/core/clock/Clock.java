package net.fossar.core.clock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clock {
    private static final int PERIOD = 10;
    private static volatile long time = 0;
    private static final Lock lock = new ReentrantLock();

    public static long currentTime() {
        return time;
    }

    public static void next() {
        lock.lock();
        time += PERIOD;
        lock.unlock();
    }

}
