package entities;

import java.util.Timer;
import java.util.TimerTask;

public interface DelayedTaskCollider extends Collider {

    default void scheduleTask() {
        if (getCurrentState() == State.COLLIDED)
            getTimer().schedule(getTimerTask(), (long) (getDelay() - getStateTime() * 1000));
    }

    default void cancelTask() {
        getTimer().cancel();
        createTimer();
    }

    void createTimer();

    Timer getTimer();

    TimerTask getTimerTask();

    long getDelay();

    State getCurrentState();

    float getStateTime();

    enum State {
        UNUSED,
        COLLIDED,
        USED,
    }
}
