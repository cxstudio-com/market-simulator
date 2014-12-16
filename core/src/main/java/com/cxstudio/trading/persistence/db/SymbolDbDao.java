package com.cxstudio.trading.persistence.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.dao.SymbolDao;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.persistence.db.mapper.SymbolMapper;

@Component
public class SymbolDbDao implements SymbolDao {
    @Autowired
    SymbolMapper symbolMapper;
    static Logger log = LoggerFactory.getLogger(SymbolDbDao.class);

    public SymbolDbDao() {
    }

    public Symbol getSymbol(String ticker) {
        return symbolMapper.selectSymbolByTicker(ticker);
    }

    public Symbol getSymbol(int symbolId) {
        return symbolMapper.selectSymbolById(symbolId);
    }

    public List<Symbol> getAllSymbols(boolean filtered) {
        return symbolMapper.selectFilteredSymbols();
    }

    public void setUpdateDate(Symbol symbol) {
    	symbolMapper.updateSymbol(symbol);
    }

}
