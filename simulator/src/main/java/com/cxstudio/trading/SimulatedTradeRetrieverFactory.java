package com.cxstudio.trading;

import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.TradeSpacing;
import com.cxstudio.trading.persistence.db.TradeDbDao;

public class SimulatedTradeRetrieverFactory implements TradeRetrieverFactory {

    private TradeDbDao tradeDao;
    private TradeSpacing tradeSpacing;
    private int bufferSize;

    public SimulatedTradeRetrieverFactory(TradeDbDao tradeDao, TradeSpacing tradeSpacing, int bufferSize) {
        super();
        this.tradeDao = tradeDao;
        this.tradeSpacing = tradeSpacing;
        this.bufferSize = bufferSize;
    }

    public TradeRetriever getTradeRetriever(Symbol symbol) {
        return new SequentialHistoricalTradeRetriever(tradeDao, symbol, tradeSpacing, bufferSize);
    }
}
