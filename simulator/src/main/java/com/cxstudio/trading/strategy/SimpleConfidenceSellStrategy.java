package com.cxstudio.trading.strategy;

import java.util.List;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.Position;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

public class SimpleConfidenceSellStrategy implements SellingStrategy {
    private float confidence = 0.8F;

    public SimpleConfidenceSellStrategy(float confidence) {
        this.confidence = confidence;
    }

    /**
     * Simulated sell strategy:
     * If sell score is high, sell all shares in open position
     */
    public SellOrder shouldSell(TradeEvaluation evaluation, TradingContext context) {
        List<Position> positions = context.getOpenPositions();
        if (evaluation.getSellScore() > confidence) {
            Trade trade = context.getCurrentTrade();

            for (Position position : positions) {
                if (position.getSymbol().equals(trade.getSymbol())) {
                    return new SellOrder(trade.getSymbol(), position.getNumOfShares(), Order.OrderType.MARKET);
                }
            }
        }
        return null;
    }
}
