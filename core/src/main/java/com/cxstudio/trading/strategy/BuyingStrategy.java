package com.cxstudio.trading.strategy;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.TradeEvaluation;

public interface BuyingStrategy {

    public BuyOrder shouldBuy(TradeEvaluation evaluation, TradingContext context);
}
