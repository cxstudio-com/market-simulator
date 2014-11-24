package com.cxstudio.trading.simulator;

import com.cxstudio.trading.Scheduler;
import com.cxstudio.trading.TradeRunner;

public class AcceleratedScheduler<T extends TradeRunner> implements Scheduler<T> {
    private final long period;
    private final long acceleratedPeriod;

    /**
     * 
     * @param period the time period that will pull data for
     * @param accelerationRate the rate the runner will accelerate based on "period" to run assigned task
     *            Ex: period = 1 minute, accelerateRate = 60, then scheduler will run every 1 second and run task
     *            on every minute data
     */
    public AcceleratedScheduler(long period, float accelerationRate) {
        this.period = period;
        this.acceleratedPeriod = (long) (period / accelerationRate);
    }

    public AcceleratedScheduler(long period, long acceleratedRate) {
        this.period = period;
        this.acceleratedPeriod = acceleratedRate;
    }

    public void start(T TradeRunner) {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }

    public long getPeriod() {
        return period;
    }

    public long getAcceleratedPeriod() {
        return acceleratedPeriod;
    }

}
