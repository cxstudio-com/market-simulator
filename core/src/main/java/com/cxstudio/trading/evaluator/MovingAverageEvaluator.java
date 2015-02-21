package com.cxstudio.trading.evaluator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class MovingAverageEvaluator implements TradeEvaluator {
    static Logger log = LoggerFactory.getLogger(MovingAverageEvaluator.class);
    private static final float BUY_TARGET = -0.02f;
    private static final float SELL_TARGET = 0.02f;

    @Override
    public TradeEvaluation evaluate(TradingContext context) {
        float sum = 0.0f;
        float avg = 0.0f;
        float percent = 0.0f;
        float buyScore = 0.0f;
        float sellScore = 0.0f;

        for (Trade trade : context.getLast30Trades()) {
            sum += trade.getClose();
        }
        avg = sum / 30;
        percent = (context.getCurrentTrade().getClose() - avg) / avg;
        percent = percent > 1 ? 1 : percent;
        if (percent < BUY_TARGET) {
            sellScore = 0f;
            buyScore = 1f;
        } else if (BUY_TARGET <= percent && percent < 0) {
            sellScore = 0f;
            buyScore = 1 - (BUY_TARGET - percent) / BUY_TARGET;
        } else if (0 < percent && percent < SELL_TARGET) {
            sellScore = 1 - (SELL_TARGET - percent) / SELL_TARGET;
            buyScore = 0f;
        } else if (SELL_TARGET < percent) {
            sellScore = 1f;
            buyScore = 0f;
        }
        return new TradeEvaluation(buyScore, sellScore);
    }

}
