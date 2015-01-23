package com.cxstudio.trading.model;

import java.util.Date;

public class Symbol {
    private int symbolId;
    private String ticker;
    private String exchange;
    private Date lastUpdate;

    public Symbol() {

    }

    public Symbol(Integer symbolId, String ticker, String exchange) {
        this.symbolId = symbolId;
        this.ticker = ticker;
        this.exchange = exchange;
    }

    public int getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(int symbolId) {
        this.symbolId = symbolId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + symbolId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Symbol other = (Symbol) obj;
        if (symbolId != other.symbolId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Symbol [symbolId=" + symbolId + ", ticker=" + ticker + ", exchange=" + exchange + ", lastUpdate=" + lastUpdate + "]";
    }

}
