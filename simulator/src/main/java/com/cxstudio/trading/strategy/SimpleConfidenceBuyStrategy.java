package com.cxstudio.trading.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class SimpleConfidenceBuyStrategy implements BuyingStrategy {
    @Value("${simulator.confidenceBuyStrategy:0.8}")
    private float confidence = 0.8F;
    private int orderSum = 10000; // dollars

    public SimpleConfidenceBuyStrategy() {
    }

    public SimpleConfidenceBuyStrategy(float confidence) {
        this.confidence = confidence;
    }

    /**
     * Simulated buying strategy:
     * Buy $10,000 worth of shares
     */
    public BuyOrder shouldBuy(TradeEvaluation evaluation, TradingContext context) {

        if (evaluation.getBuyScore() > confidence && context.getAvailableFund() > orderSum) {
            Trade trade = context.getCurrentTrade();
            BuyOrder order = new BuyOrder(trade.getSymbol(), (int) (orderSum / trade.getClose()),
                    Order.OrderType.MARKET, trade.getClose(), trade.getClose(), trade.getDateTime());
            return order;
        } else {
            return null;
        }
    }
}
