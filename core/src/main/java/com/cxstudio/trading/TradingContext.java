package com.cxstudio.trading;

import java.util.List;

import com.cxstudio.trading.model.Position;
import com.cxstudio.trading.model.Trade;

public class TradingContext {

    private float availableFund;
    private Trade currentTrade;
    private List<Trade> last30Trades;
    private List<Position> openPositions;

    public Trade getCurrentTrade() {
        return currentTrade;
    }

    public float getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(float availableFund) {
        this.availableFund = availableFund;
    }

    public void setCurrentTrade(Trade cunrrentTrade) {
        this.currentTrade = cunrrentTrade;
    }

    public List<Trade> getLast30Trades() {
        return last30Trades;
    }

    public void setLast30Trades(List<Trade> last30Trades) {
        this.last30Trades = last30Trades;
    }

    public List<Position> getOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(List<Position> openPositions) {
        this.openPositions = openPositions;
    }

}
