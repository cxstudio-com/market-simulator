package com.cxstudio.trading.strategy;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class RandomBuyStrategy implements BuyingStrategy {

    public BuyOrder shouldBuy(TradeEvaluation evaluation, TradingContext context) {
        Portfolio portfolio = context.getPortfolio();
        Random random = new Random(System.nanoTime());
        Trade trade = context.getCurrentTrade();
        if (portfolio.getAvailableCash() > 1020 && random.nextBoolean()) {
            return new BuyOrder(trade.getSymbol(), (int) (10000 / trade.getClose()), Order.OrderType.MARKET, trade.getClose(), trade.getClose());
        } else {
            return null;
        }
    }

}
