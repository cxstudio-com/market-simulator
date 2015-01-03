package com.cxstudio.trading.persistence.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.DataFilter;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.persistence.db.mapper.TradeMapper;

@Component
public class TradeDbDao implements TradeDao {
    static Logger log = LoggerFactory.getLogger(TradeDbDao.class);
    @Autowired
    TradeMapper mapper;

    public TradeDbDao() {
    }

    public List<Trade> getTrades(Symbol symbol, DataFilter filter) {
        return mapper.selectTrades(symbol, filter);
    }
    
    public List<Trade> getMiddleBufferedTrades(Symbol symbol, DataFilter filter) {
        return mapper.selectMiddleBufferedTrades(symbol, filter);
    }

    public void insertTrades(List<Trade> trades) {
        if (trades != null && trades.size() > 0)
            mapper.insertTrades(trades);
    }

    public void insertTempTrades(List<Trade> trades) {
        if (trades != null && trades.size() > 0)
            mapper.insertTempTrades(trades);
    }
}
