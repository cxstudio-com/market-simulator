package com.cxstudio.trading;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.persistence.TradeDao;

public class SequentialHistoricalTradeRetriever implements TradeRetriever {
    private final TradeDao tradeDao;
    private final Symbol symbol;
    private static final int BUFFER_SIZE = 80;
    private LinkedList<Trade> tradeBuffer = new LinkedList<Trade>();
    private DataFilter filter;

    public SequentialHistoricalTradeRetriever(TradeDao tradeDao, Symbol symbol) {
        this.tradeDao = tradeDao;
        this.symbol = symbol;
        filter = new DataFilter();
        filter.setLimit(BUFFER_SIZE);
    }

    public Trade retrieve(Date dateTime) {
        if (tradeBuffer.size() == 0) {
            filter.setStartTime(dateTime);
            tradeBuffer.addAll(tradeDao.getTrades(symbol, filter));
        }
        return tradeBuffer.poll();
    }

    public List<Trade> lastNumOfTrades(Date startTime, int numOfTrades) {
        // TODO Auto-generated method stub
        return null;
    }
}
