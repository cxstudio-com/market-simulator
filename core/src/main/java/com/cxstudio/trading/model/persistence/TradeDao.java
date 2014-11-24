package com.cxstudio.trading.model.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.persistence.mapper.TradeMapper;

public class TradeDao {
    static Logger log = LoggerFactory.getLogger(TradeDao.class);
    @Autowired
    TradeMapper mapper;

    public TradeDao() {
    }

    public List<Trade> getTrades(Symbol symbol, DataFilter filter) {
        return mapper.selectTrades(symbol, filter);
    }

    public void insertTrades(List<Trade> trades) throws Exception {
        if (trades != null && trades.size() > 0)
            mapper.insertTrades(trades);
    }

    public void insertTempTrades(List<Trade> trades) throws Exception {
        if (trades != null && trades.size() > 0)
            mapper.insertTempTrades(trades);
    }
}
