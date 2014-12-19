package com.cxstudio.trading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;

@Component
public class PortfolioManager {
    private Portfolio portfolio;
    @Autowired
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
