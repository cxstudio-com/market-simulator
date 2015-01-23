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
        Trade trade = context.getCurrentTrade();
        if (portfolio.getAvailableCash() > 1020 && randomPick()) {
            return new BuyOrder(trade.getSymbol(), (int) (10000 / trade.getClose()), Order.OrderType.MARKET, trade.getClose(), trade.getClose(), trade.getDateTime());
        } else {
            return null;
        }
    }

    private boolean randomPick() {
        Random random = new Random(System.nanoTime());
        return (random.nextInt() % 10 == 0);
    }

}
