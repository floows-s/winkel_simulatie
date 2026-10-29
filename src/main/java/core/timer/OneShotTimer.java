package core.timer;

public class OneShotTimer {
    private double waitTimeMS;
    private Runnable action = null;
    private long firstTickTimeNS = -1;

    public OneShotTimer(double waitTimeMS, Runnable action){
        this.waitTimeMS = waitTimeMS;
        this.action = action;
    }

    public OneShotTimer(double waitTimeMS){
        this.waitTimeMS = waitTimeMS;
    }

    public boolean tick(long nowNS){
        if(firstTickTimeNS <= 0){
            firstTickTimeNS = nowNS;
            return false;
        }

        double timePassedMS = (nowNS - firstTickTimeNS) / 1E6;
        if(timePassedMS >= waitTimeMS){
            if(action != null) action.run();
            return true;
        }

        return false;
    }

    public void reset(){
        firstTickTimeNS = -1;
    }
}
