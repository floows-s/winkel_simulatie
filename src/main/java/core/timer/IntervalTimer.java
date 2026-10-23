package core.timer;


public class IntervalTimer{
    private double intervalTime_MS;
    private long previousIntervalPassTime_NS;
    private Runnable action;

    /// The interval timer will execute the provided {@code action} every {@code intervalTime} when checked with {@link tick}.
    /// <p>
    /// If no action is provided the return value of {@link tick} can still be used to determine if the interval time has been reached that tick.
    /// @param intervalTime in milliseconds
    public IntervalTimer(double intervalTime){
        this.intervalTime_MS = intervalTime;
    }

    /// The interval timer will execute the provided {@code action} every {@code intervalTime} when checked with {@link tick}.
    /// <p>
    /// If no action is provided the return value of {@link tick} can still be used to determine if the interval time has been reached that tick.
    /// @param intervalTime in milliseconds
    /// @param action to execute after threshold has been reached
    public IntervalTimer(double intervalTime, Runnable action){
        this.intervalTime_MS = intervalTime;
        this.action = action;
    }

    /// Checks if the interval time has been reached.
    /// <p>
    /// If so, it will execute the {@code action}, if any has been provided, and return {@code true}. Otherwise it will return false.
    /// @param now in nanoseconds
    /// @return if the interval time has been reached this tick.
    public boolean tick(long now){
        // If previous time has not been set -> set it
        if(previousIntervalPassTime_NS == 0){
            previousIntervalPassTime_NS = now;
            return false;
        }

        // Check if enough time has passed
        double timePassed_MS = (now - previousIntervalPassTime_NS) / 1e6;
        if(timePassed_MS > intervalTime_MS){
            previousIntervalPassTime_NS = now;

            if(action != null) action.run();
            return true;
        }

        return false;
    }

    /// @param action to execute when interval time has been reached.
    public void setAction(Runnable action) {
        this.action = action;
    }

    /// @param intervalTime in milliseconds
    public void setIntervalTime(double intervalTime) {
        this.intervalTime_MS = intervalTime;
    }
}
