package com.cxstudio.trading;

import com.cxstudio.trading.model.TradeEvaluation;

public interface TradeEvaluator {

    public TradeEvaluation evaluate(TradingContext context);
}
