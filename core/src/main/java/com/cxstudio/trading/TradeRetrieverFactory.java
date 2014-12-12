package com.cxstudio.trading;

import com.cxstudio.trading.model.Symbol;

public interface TradeRetrieverFactory {
    public TradeRetriever getTradeRetriever(Symbol symbol);
}
