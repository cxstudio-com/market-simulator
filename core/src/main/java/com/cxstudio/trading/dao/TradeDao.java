package com.cxstudio.trading.dao;

import java.util.List;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;

public interface TradeDao {
    public List<Trade> getTrades(Symbol symbol, DataFilter filter);

    public void insertTrades(List<Trade> trades);

    public void insertTempTrades(List<Trade> trades);
}
