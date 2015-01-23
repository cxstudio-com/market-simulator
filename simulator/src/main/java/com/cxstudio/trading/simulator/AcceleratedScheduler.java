package com.cxstudio.trading.simulator;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxstudio.trading.Scheduler;
import com.cxstudio.trading.TradingDates;
import com.cxstudio.trading.dao.TradeDao;

public class AcceleratedScheduler implements Scheduler<BatchTradeRunner> {
    static Logger log = LoggerFactory.getLogger(AcceleratedScheduler.class);
    private final long periodInSecond;
    private final long actualPeriodInMillis;
    private final BatchTradeRunner tradeRunner;
    private final Date startTime;
    private final TradeDao tradeDao;
    private final static int TRADE_DAY_START_HOUR = 9;
    private final static int TRADE_DAY_START_MIN = 30;
    private final static int TRADE_DAY_END_HOUR = 15;
    private final static int TRADE_DAY_END_MIN = 59;

    /**
     * 
     * @param period the time period that will pull data for
     * @param accelerationRate the rate the runner will accelerate based on "period" to run assigned task
     *            Ex: period = 1 minute, accelerateRate = 60, then scheduler will run every 1 second and run task
     *            on every minute data
     */
    public AcceleratedScheduler(BatchTradeRunner tradeRunner, Date startTime, long periodInSecond, float accelerationRate, TradeDao tradeDao) {
        this.startTime = startTime;
        this.periodInSecond = periodInSecond;
        this.actualPeriodInMillis = (long) (periodInSecond * 1000 * accelerationRate);
        this.tradeRunner = tradeRunner;
        this.tradeDao = tradeDao;
    }

    public AcceleratedScheduler(BatchTradeRunner tradeRunner, Date startTime, long periodInSecond, long actualPeriodInMillis, TradeDao tradeDao) {
        this.startTime = startTime;
        this.periodInSecond = periodInSecond;
        this.actualPeriodInMillis = actualPeriodInMillis;
        this.tradeRunner = tradeRunner;
        this.tradeDao = tradeDao;
    }

    public void start() {
        RunnerTask task = new RunnerTask(tradeRunner, startTime, periodInSecond);
        Timer timer = new Timer();
        timer.schedule(task, 0, actualPeriodInMillis);
    }

    public void stop() {
        // TODO Auto-generated method stub
    }

    public long getPeriodInSecond() {
        return periodInSecond;
    }

    public long getAcceleratedPeriod() {
        return actualPeriodInMillis;
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

        /**
         * TODO:
         * Running time needs to be validated against valid trading day/hour, and skip off hours weekend and holidays
         */
        @Override
        public void run() {
            runningTime += periodInSeconds * 1000;
            Date timeToRun = new Date(runningTime);
            Calendar tradeStart = Calendar.getInstance();
            tradeStart.setTimeInMillis(runningTime);
            tradeStart.set(Calendar.HOUR_OF_DAY, TRADE_DAY_START_HOUR);
            tradeStart.set(Calendar.MINUTE, TRADE_DAY_START_MIN);
            tradeStart.set(Calendar.SECOND, 0);
            Calendar tradeEnd = Calendar.getInstance();
            tradeEnd.setTimeInMillis(runningTime);
            tradeEnd.set(Calendar.HOUR_OF_DAY, TRADE_DAY_END_HOUR);
            tradeEnd.set(Calendar.MINUTE, TRADE_DAY_END_MIN);
            tradeEnd.set(Calendar.SECOND, 0);

            // fix start time that is not during trading hours
            if (tradeStart.getTime().after(timeToRun) || tradeEnd.getTime().before(timeToRun)) {
                if (tradeEnd.getTime().before(timeToRun)) {
                    tradeStart.add(Calendar.DATE, 1);
                }
                timeToRun = tradeStart.getTime();
                if (!TradingDates.isBusinessDay(timeToRun)) {
                    log.info("{} is not a business day skip", timeToRun);
                    timeToRun = TradingDates.getNextBusinessDay(timeToRun);
                }
                runningTime = timeToRun.getTime();
            }

            log.debug("Running batch task on {}", timeToRun);
            runner.run(timeToRun);
        }

    }

}
