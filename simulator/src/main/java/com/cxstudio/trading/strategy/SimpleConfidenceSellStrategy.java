package com.cxstudio.trading.strategy;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.OpenPosition;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class SimpleConfidenceSellStrategy implements SellingStrategy {

    static Logger log = LoggerFactory.getLogger(SimpleConfidenceSellStrategy.class);
    @Value("${simulator.confidenceBuyStrategy:0.8}")
    private float confidence = 0.8F;

    public SimpleConfidenceSellStrategy() {
    }

    public SimpleConfidenceSellStrategy(float confidence) {
        this.confidence = confidence;
    }

    /**
     * Simulated sell strategy:
     * If sell score is high, sell all shares in open position
     */
    public SellOrder shouldSell(TradeEvaluation evaluation, TradingContext context) {
        Set<OpenPosition> positions = context.getPortfolio().getOpenPositions();
        if (evaluation.getSellScore() > confidence) {
            Trade trade = context.getCurrentTrade();

            for (OpenPosition position : positions) {
                if (position.getSymbol().equals(trade.getSymbol())) {
                    return new SellOrder(trade.getSymbol(), position.getNumOfShares(), Order.OrderType.MARKET, trade.getClose(), trade.getClose(), trade.getDateTime());
                }
            }
        }
        return null;
    }
}
