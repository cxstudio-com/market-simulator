package com.cxstudio.trading.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class SimplePercentProfitSellStrategy implements SellingStrategy {
    @Value("${simulator.percentProfitSellStrategyValue:0.03}")
    private float profitPercent = 0.03f;

    public SimplePercentProfitSellStrategy() {

    }

    public SimplePercentProfitSellStrategy(float profitPercent) {
        this.profitPercent = profitPercent;
    }

    public SellOrder shouldSell(TradeEvaluation evaluation, TradingContext context) {
        Trade currentTrade = context.getCurrentTrade();
        Portfolio portfolio = context.getPortfolio();
        int numOfShares = portfolio.getSharesPerSymbol(currentTrade.getSymbol());
        float totalCost = portfolio.totalCostPerSymbol(currentTrade.getSymbol());
        float totalProceed = currentTrade.getClose() * numOfShares;

        if (((totalProceed - totalCost) / totalCost) > profitPercent) {
            return new SellOrder(currentTrade.getSymbol(), numOfShares, Order.OrderType.MARKET,
                    currentTrade.getClose(), currentTrade.getClose(), currentTrade.getDateTime());
        } else {
            return null;
        }
    }
}
