package com.cxstudio.trading;

import java.util.HashMap;
import java.util.Map;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;

@SuppressWarnings("boxing")
public class PortfolioManager {
    private Portfolio portfolio;
    private OrderExecutor orderExecutor;
    private final Map<Symbol, Float> latestQuotes;

    public PortfolioManager(OrderExecutor orderExecutor) {
        this.orderExecutor = orderExecutor;
        this.latestQuotes = new HashMap<Symbol, Float>();
    }

    public void executeOrder(Order order) {
        synchronized (portfolio) {
            orderExecutor.execute(order, portfolio);
        }
    }

    public void updateLatestQuote(Trade trade) {
        latestQuotes.put(trade.getSymbol(), trade.getClose());
    }

    public double getTotalOpenPositionWorth() {
        double total = portfolio.getOpenPositions().stream()
                .mapToDouble(position -> latestQuotes.get(position.getSymbol()) * position.getNumOfShares()).sum();
        return total;
    }

    public double getCurrentPortfolioWorth() {
        return getTotalOpenPositionWorth() + portfolio.getAvailableCash();
    }

    public OrderExecutor getOrderExecutor() {
        return orderExecutor;
    }

    public void setOrderExecutor(OrderExecutor orderExecutor) {
        this.orderExecutor = orderExecutor;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}
