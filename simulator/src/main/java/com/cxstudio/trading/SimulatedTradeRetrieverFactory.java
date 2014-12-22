package com.cxstudio.trading;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.TradeSpacing;
import com.cxstudio.trading.persistence.db.TradeDbDao;

public class SimulatedTradeRetrieverFactory implements TradeRetrieverFactory {

    private TradeDao tradeDao;
    private TradeSpacing tradeSpacing;
    private int bufferSize;

    public SimulatedTradeRetrieverFactory(TradeDao tradeDao, TradeSpacing tradeSpacing, int bufferSize) {
        super();
        this.tradeDao = tradeDao;
        this.tradeSpacing = tradeSpacing;
        this.bufferSize = bufferSize;
    }

    public TradeRetriever getTradeRetriever(Symbol symbol) {
        return new SequentialHistoricalTradeRetriever(tradeDao, symbol, tradeSpacing, bufferSize);
    }
}
