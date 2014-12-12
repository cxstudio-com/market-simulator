package com.cxstudio.trading.dao;

import java.util.List;

import com.cxstudio.trading.model.Symbol;

public interface SymbolDao {
    public Symbol getSymbol(String ticker);

    public Symbol getSymbol(int symbolId);

    public List<Symbol> getAllSymbols(boolean filtered);

    public void setUpdateDate(Symbol symbol);
}
