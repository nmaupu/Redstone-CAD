package net.fossar.model.core.clock;

public class Time {
    private final long creationTime;

    public Time() {
        creationTime = Clock.currentTime();
    }

    public long getTime() {
        return creationTime;
    }


    public boolean isExpired(long delay) {
        return creationTime + delay <= Clock.currentTime();
    }

}
