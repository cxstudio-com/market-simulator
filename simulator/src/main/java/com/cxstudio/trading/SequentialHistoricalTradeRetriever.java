package com.cxstudio.trading;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeSpacing;
import com.cxstudio.trading.persistence.db.TradeDbDao;

public class SequentialHistoricalTradeRetriever implements TradeRetriever {
    private final TradeDao tradeDao;
    private final Symbol symbol;
    private final int bufferSize = 80;
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
            // get n number (n=bufferSize) of trades, centered at given dateTime with n/2 number of trades
            // at before and after the giving date.
            List<Trade> trades = fetchCache(dateTime);
            if (trades != null && trades.size() > 0) {
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
                return tradeBuffer.get(currentIndex);
            } else if (compare > 0) {
                if (compareDate(tradeBuffer.get(0), dateTime, tradeSpacing) <= 0) {
                    // if the first one in buffer is still
                    currentIndex = 0; // start from begining and iterate it again
                } else {
                    // if we starts from index=0, we should never reach trade date > given dateTime, re-fetch
                    tradeBuffer.clear();
                }
                return retrieve(dateTime);
            } // else compare < 0, trade date < given dateTime, then proceed to next trade
        }
        // if index meets the end, fetch another n number of trades
        return retrieve(dateTime);
    }

    private List<Trade> fetchCache(Date dateTime) {
        currentIndex = 0;
        filter.setStartTime(getStartingDate(dateTime, tradeSpacing.getMilliseconds(), bufferSize / 2 * (-1)));
        return tradeDao.getTrades(symbol, filter);
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
