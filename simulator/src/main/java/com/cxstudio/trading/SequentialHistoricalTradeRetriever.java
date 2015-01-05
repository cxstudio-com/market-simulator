package com.cxstudio.trading;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeSpacing;

/**
 * TODO: caching logic is still flawed. Refetching should happen when half the cache is reached not when the end
 * 
 * @author mikecao
 *
 */
public class SequentialHistoricalTradeRetriever implements TradeRetriever {
    static Logger log = LoggerFactory.getLogger(SequentialHistoricalTradeRetriever.class);
    private final TradeDao tradeDao;
    private final Symbol symbol;
    private final int bufferSize = 60;
    private LinkedList<Trade> tradeBuffer = new LinkedList<Trade>();
    private DataFilter filter;
    private TradeSpacing tradeSpacing;
    private int currentIndex = 0;

    public SequentialHistoricalTradeRetriever(TradeDao tradeDao, Symbol symbol, TradeSpacing tradeSpacing, int bufferSize) {
        this.tradeDao = tradeDao;
        this.symbol = symbol;
        this.tradeSpacing = tradeSpacing;
        filter = new DataFilter();
        filter.setInterval(tradeSpacing.getSeconds());
        filter.setLimit(bufferSize);
    }

    public Trade retrieve(Date dateTime) {
        if (tradeBuffer.size() <= 0) { // initial fetch
            log.trace("Buffer size 0. Fetch from source for {}", dateTime);
            // get n number (n=bufferSize) of trades, centered at given dateTime with n/2 number of trades
            // at before and after the giving date.
            List<Trade> trades = fetchCache(dateTime);
            log.trace("{} trades fetched from source.", (trades == null ? "null" : trades.size()));
            if (trades != null && trades.size() > 0) {
                log.trace("First one from the fetched data {}", trades.get(0));
                tradeBuffer.addAll(trades);
            } else {
                return null;
            }
        }

        // the caching policy:
        // advance idx pointer until cached trade matches the given time.
        for (; currentIndex < tradeBuffer.size(); currentIndex++) {
            int compare = compareDate(tradeBuffer.get(currentIndex), dateTime, tradeSpacing);
            if (compare == 0) {
                log.trace("Returning trade {}", tradeBuffer.get(currentIndex));
                return tradeBuffer.get(currentIndex);
            } else if (compare > 0) {
                log.trace("tradeBuffer.get(currentIndex) {} > dateTime {}. move to next", tradeBuffer.get(currentIndex), dateTime);
                if (compareDate(tradeBuffer.get(0), dateTime, tradeSpacing) <= 0) {
                    // if the first one in buffer is earlier than the given date start from begining and iterate it
                    // again
                    log.debug("The first one in the buffer {} <= given time {}. Iterate from first again", tradeBuffer.get(0), dateTime);
                    currentIndex = 0;
                } else {
                    // if we starts from index=0, we should never reach trade date > given dateTime, re-fetch
                    log.debug("The first one in the buffer {} > given time {}. Clear cache and refetch.", tradeBuffer.get(0), dateTime);
                    tradeBuffer.clear();
                }
                return retrieve(dateTime);
            } else { // else compare < 0, trade date < given dateTime, then proceed to next trade
                log.trace("move to next trade in buffer. tradeBuffer.get(currentIndex) {} < dateTime {}.", tradeBuffer.get(currentIndex), dateTime);
            }
        }
        try {
            log.debug("index meets the end, fetch another n number of trades. datetime: {}", dateTime);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // if index meets the end, fetch another n number of trades
        tradeBuffer.clear();
        return retrieve(dateTime);
    }

    private List<Trade> fetchCache(Date dateTime) {
        currentIndex = 0;
        filter.setStartTime(dateTime);
        filter.setLimit(bufferSize / 2);
        return tradeDao.getMiddleBufferedTrades(symbol, filter);
    }

    public List<Trade> lastNumOfTrades(Date startTime, int numOfTrades) {
        if (numOfTrades > bufferSize / 2) {
            throw new IllegalArgumentException("Requested last " + numOfTrades + " exceeds half of the cached size " + bufferSize / 2);
        }
        if (retrieve(startTime) == null) {
            return null; // no data for given time
        } else {
            return tradeBuffer.subList(currentIndex - numOfTrades > 0 ? currentIndex - numOfTrades : 0, currentIndex);
        }
    }

    private Date getStartingDate(Date time, long tradeSpacing, int stepsAway) {
        return new Date(time.getTime() + tradeSpacing * stepsAway / 2);
    }

    private int compareDate(Trade trade, Date time, TradeSpacing tradeSpacing) {
        Date toTime = new Date(time.getTime() + tradeSpacing.getMilliseconds());
        Date tradeTime = trade.getDateTime();
        int compareValue = 0;
        if (!tradeTime.before(time) && tradeTime.before(toTime)) {
            return 0;
        } else if (tradeTime.before(time)) {
            compareValue = -1;
        } else if (tradeTime.after(toTime)) {
            compareValue = 1;
        }
        return compareValue;

    }
}
