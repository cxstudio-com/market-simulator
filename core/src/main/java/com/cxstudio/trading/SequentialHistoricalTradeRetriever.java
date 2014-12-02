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
        filter.setInterval(tradeSpacing.getSpacing());
        filter.setLimit(bufferSize);
    }

    public Trade retrieve(Date dateTime) {
        if (tradeBuffer.size() <= 0) { // initial fetch
            filter.setStartTime(getStartingDate(dateTime, tradeSpacing.getSpacing(), bufferSize / 2 * (-1)));
            tradeBuffer.addAll(tradeDao.getTrades(symbol, filter));
        }

        for (currentIndex = 0; currentIndex < tradeBuffer.size(); currentIndex++) {
            int compare = compareDate(tradeBuffer.get(currentIndex), dateTime, tradeSpacing.getSpacing());
            if (compare == 0) {
                return tradeBuffer.get(currentIndex);
            } else if (compare < 0) {
                return null; //
            }
        }
        return tradeBuffer.poll();
    }

    public List<Trade> lastNumOfTrades(Date startTime, int numOfTrades) {
        // TODO Auto-generated method stub
        return null;
    }

    private position
    private Date getStartingDate(Date time, int tradeSpacing, int stepsAway) {
        return new Date(time.getTime() + tradeSpacing * 1000 * stepsAway / 2);
    }

    private int compareDate(Trade trade, Date time, int tradeSpacing) {
        Date toTime = new Date(time.getTime() + tradeSpacing * 1000);
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
