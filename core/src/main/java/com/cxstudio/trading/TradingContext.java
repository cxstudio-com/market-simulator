package com.cxstudio.trading;

import java.util.List;

import com.cxstudio.trading.model.OpenPosition;
import com.cxstudio.trading.model.Trade;

public class TradingContext {

    private Trade cunrrentTrade;
    private List<Trade> last30Trades;
    private List<OpenPosition> openPositions;

    public Trade getCunrrentTrade() {
        return cunrrentTrade;
    }

    public void setCunrrentTrade(Trade cunrrentTrade) {
        this.cunrrentTrade = cunrrentTrade;
    }

    public List<Trade> getLast30Trades() {
        return last30Trades;
    }

    public void setLast30Trades(List<Trade> last30Trades) {
        this.last30Trades = last30Trades;
    }

    public List<OpenPosition> getOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(List<OpenPosition> openPositions) {
        this.openPositions = openPositions;
    }

}
