package com.cxstudio.trading.simulator;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxstudio.trading.Scheduler;
import com.cxstudio.trading.TradingDates;
import com.cxstudio.trading.dao.TradeDao;

public class SequentialScheduler implements Scheduler<BatchTradeRunner> {
    static Logger log = LoggerFactory.getLogger(SequentialScheduler.class);
    private final long periodInSecond;
    private final BatchTradeRunner tradeRunner;
    private final Date startTime;
    private final Date stopTime;
    private final TradeDao tradeDao;
    private final static int TRADE_DAY_START_HOUR = 9;
    private final static int TRADE_DAY_START_MIN = 30;
    private final static int TRADE_DAY_END_HOUR = 15;
    private final static int TRADE_DAY_END_MIN = 59;

    public SequentialScheduler(BatchTradeRunner tradeRunner, Date startTime, Date stopTime, long periodInSecond,
            TradeDao tradeDao) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.periodInSecond = periodInSecond;
        this.tradeRunner = tradeRunner;
        this.tradeDao = tradeDao;
    }

    public void start() {
        long runningTime = startTime.getTime();
        while (runningTime < stopTime.getTime()) {
            runningTime += this.periodInSecond * 1000;
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
                    log.debug("{} is not a business day skip", timeToRun);
                    timeToRun = TradingDates.getNextBusinessDay(timeToRun);
                }
                runningTime = timeToRun.getTime();
            }

            log.debug("Running batch task on {}", timeToRun);
            tradeRunner.run(timeToRun);
        }
    }

    public void stop() {
        // TODO Auto-generated method stub

    }

}
