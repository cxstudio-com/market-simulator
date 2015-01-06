package com.cxstudio.trading;

import java.util.HashMap;
import java.util.Map;

import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.TradeSpacing;

public class SimulatedTradeRetrieverFactory implements TradeRetrieverFactory {

    private TradeDao tradeDao;
    private TradeSpacing tradeSpacing;
    private int bufferSize;
    private Map<Symbol, TradeRetriever> tradeRetrieverMap;

    public SimulatedTradeRetrieverFactory(TradeDao tradeDao, TradeSpacing tradeSpacing, int bufferSize) {
        super();
        this.tradeDao = tradeDao;
        this.tradeSpacing = tradeSpacing;
        this.bufferSize = bufferSize;
        this.tradeRetrieverMap = new HashMap<Symbol, TradeRetriever>();
    }

    public TradeRetriever getTradeRetriever(Symbol symbol) {
        if (!tradeRetrieverMap.containsKey(symbol)) {
            tradeRetrieverMap.put(symbol, new SequentialHistoricalTradeRetriever(tradeDao, symbol, tradeSpacing, bufferSize));
        }
        return tradeRetrieverMap.get(symbol);
    }
}
