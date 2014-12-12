package com.cxstudio.trading;

import java.util.List;

import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.Trade;

public class TradingContext {

    private float availableFund;
    private Trade currentTrade;
    private List<Trade> last30Trades;
    private Portfolio portfolio;

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

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio profile) {
        this.portfolio = profile;
    }

}
