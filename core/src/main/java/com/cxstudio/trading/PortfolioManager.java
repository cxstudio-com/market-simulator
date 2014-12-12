package com.cxstudio.trading;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;

public class PortfolioManager {
    private Portfolio portfolio;
    private OrderExecutor orderExecutor;

    public PortfolioManager(OrderExecutor orderExecutor) {
        this.orderExecutor = orderExecutor;
    }

    public void executeOrder(Order order) {
        synchronized (portfolio) {
            orderExecutor.execute(order, portfolio);
        }
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
