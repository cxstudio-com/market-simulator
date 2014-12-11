package com.cxstudio.trading.strategy;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.TradeEvaluation;

public interface SellingStrategy {

    public SellOrder shouldSell(TradeEvaluation evaluation, TradingContext context);
}
