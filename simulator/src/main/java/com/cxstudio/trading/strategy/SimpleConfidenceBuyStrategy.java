package com.cxstudio.trading.strategy;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class SimpleConfidenceBuyStrategy implements BuyingStrategy {
    private float confidence = 0.8F;

    public SimpleConfidenceBuyStrategy(float confidence) {
        this.confidence = confidence;
    }

    /**
     * Simulated buying strategy:
     * Buy $10,000 worth of shares
     */
    public BuyOrder shouldBuy(TradeEvaluation evaluation, TradingContext context) {
        if (evaluation.getBuyScore() > confidence) {
            Trade trade = context.getCurrentTrade();
            BuyOrder order = new BuyOrder(trade.getSymbol(), (int) (10000 / trade.getClose()), Order.OrderType.MARKET);
            return order;
        } else {
            return null;
        }
    }
}
