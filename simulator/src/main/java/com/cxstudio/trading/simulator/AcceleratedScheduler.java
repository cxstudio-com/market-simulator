package com.cxstudio.trading.simulator;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.cxstudio.trading.Scheduler;

public class AcceleratedScheduler implements Scheduler<BatchTradeRunner> {
    private final long period;
    private final long actualPeriodInSeconds;
    private final BatchTradeRunner tradeRunner;
    private final Date startTime;

    /**
     * 
     * @param period the time period that will pull data for
     * @param accelerationRate the rate the runner will accelerate based on "period" to run assigned task
     *            Ex: period = 1 minute, accelerateRate = 60, then scheduler will run every 1 second and run task
     *            on every minute data
     */
    public AcceleratedScheduler(BatchTradeRunner tradeRunner, Date startTime, long periodInSeconds, float accelerationRate) {
        this.startTime = startTime;
        this.period = periodInSeconds;
        this.actualPeriodInSeconds = (long) (period / accelerationRate);
        this.tradeRunner = tradeRunner;
    }

    public AcceleratedScheduler(BatchTradeRunner tradeRunner, Date startTime, long periodInSeconds, long actualPeriodInSeconds) {
        this.startTime = startTime;
        this.period = periodInSeconds;
        this.actualPeriodInSeconds = actualPeriodInSeconds;
        this.tradeRunner = tradeRunner;
    }

    public void start() {
        RunnerTask task = new RunnerTask(tradeRunner, startTime, period);
        Timer timer = new Timer();
        timer.schedule(task, 0, actualPeriodInSeconds * 1000);
    }

    public void stop() {
        // TODO Auto-generated method stub
    }

    public long getPeriod() {
        return period;
    }

    public long getAcceleratedPeriod() {
        return actualPeriodInSeconds;
    }

    private class RunnerTask extends TimerTask {
        BatchTradeRunner runner;
        long periodInSeconds;
        long runningTime;

        private RunnerTask(BatchTradeRunner runner, Date startTime, long periodInSeconds) {
            this.runner = runner;
            this.periodInSeconds = periodInSeconds;
            this.runningTime = startTime.getTime();
        }

        @Override
        public void run() {
            runningTime += periodInSeconds * 1000;
            runner.run(new Date(runningTime));
        }

    }

}
