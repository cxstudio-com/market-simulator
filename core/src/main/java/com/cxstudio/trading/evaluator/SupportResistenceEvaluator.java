package com.cxstudio.trading.evaluator;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class SupportResistenceEvaluator implements TradeEvaluator {

    public TradeEvaluation evaluate(TradingContext context) {
        // TODO Auto-generated method stub
        return new TradeEvaluation(1, 0);
    }

}
