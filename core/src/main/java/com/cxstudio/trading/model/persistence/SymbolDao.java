package com.cxstudio.trading.model.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.persistence.mapper.SymbolMapper;

public class SymbolDao {
    @Autowired
    SymbolMapper mapper;
    static Logger log = LoggerFactory.getLogger(SymbolDao.class);

    public SymbolDao() {
    }

    public Symbol getSymbol(String ticker) {
        return mapper.selectSymbolByTicker(ticker);
    }

    public Symbol getSymbol(int symbolId) {
        return mapper.selectSymbolById(symbolId);
    }

    public List<Symbol> getAllSymbols(boolean filtered) {
        return mapper.selectFilteredSymbols();
    }

    public void setUpdateDate(Symbol symbol) {
        mapper.updateSymbol(symbol);
    }

}
