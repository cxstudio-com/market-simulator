package com.cxstudio.trading.strategy;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cxstudio.trading.TradingContext;
import com.cxstudio.trading.model.Order;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

@Component
public class RandomSellStrategy implements SellingStrategy {
    static Logger log = LoggerFactory.getLogger(RandomSellStrategy.class);

    public SellOrder shouldSell(TradeEvaluation evaluation, TradingContext context) {
        Portfolio portfolio = context.getPortfolio();
        Trade trade = context.getCurrentTrade();
        int sharesHeld = portfolio.getSharesPerSymbol(trade.getSymbol());
        if (sharesHeld > 0 && randomPick()) {
            return new SellOrder(trade.getSymbol(), sharesHeld, Order.OrderType.MARKET, trade.getClose(), trade.getClose(), trade.getDateTime());
        } else {
            return null;
        }
    }

    private boolean randomPick() {
        Random random = new Random(System.nanoTime());
        return (random.nextInt() % 10 == 0);
    }

}
