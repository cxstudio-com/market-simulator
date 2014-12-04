package com.cxstudio.trading;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeSpacing;
import com.cxstudio.trading.model.persistence.TradeDao;

public class SequentialHistoricalTradeRetriever implements TradeRetriever {
    private final TradeDao tradeDao;
    private final Symbol symbol;
    private final int bufferSize = 80;
    private LinkedList<Trade> tradeBuffer = new LinkedList<Trade>();
    private DataFilter filter;
    private TradeSpacing tradeSpacing;
    private int currentIndex;

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
            filter.setStartTime(getStartingDate(dateTime, tradeSpacing.getMilliseconds(), bufferSize / 2 * (-1)));
            tradeBuffer.addAll(tradeDao.getTrades(symbol, filter));
        }

        // the caching policy:
        // advance idx pointer until cached trade matches the given time.
        // if index > mid point, delete the last item as it advances.
        // if index meets the end, fetch another n/2 number of trades
        for (currentIndex = 0; currentIndex < tradeBuffer.size(); currentIndex++) {
            int compare = compareDate(tradeBuffer.get(currentIndex), dateTime, tradeSpacing);
            if (compare == 0) {
            	if 
                return tradeBuffer.get(currentIndex);
            } else if (compare < 0) {
                return null; //
            }
        }
        return tradeBuffer.poll();
    }
    
    private List<Trade> fetchCache(int cacheSize, Date dateTime) {
    	
    }

    public List<Trade> lastNumOfTrades(Date startTime, int numOfTrades) {
        // TODO Auto-generated method stub
        return null;
    }

    private position
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
