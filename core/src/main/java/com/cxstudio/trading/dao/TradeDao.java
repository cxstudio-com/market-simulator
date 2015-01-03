package com.cxstudio.trading.dao;

import java.util.List;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;

public interface TradeDao {
    List<Trade> getTrades(Symbol symbol, DataFilter filter);
    
    List<Trade> getMiddleBufferedTrades(Symbol symbol, DataFilter filter);

    void insertTrades(List<Trade> trades);

    void insertTempTrades(List<Trade> trades);
}
