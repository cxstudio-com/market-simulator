package com.cxstudio.trading;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;
import com.cxstudio.trading.strategy.BuyingStrategy;
import com.cxstudio.trading.strategy.SellingStrategy;

public class SingleTradeRunner implements TradeRunner {
    static Logger log = LoggerFactory.getLogger(SingleTradeRunner.class);
    private final Set<TradeEvaluator> evaluators;
    private final Symbol symbol;
    private final PortfolioManager portfolioManager;
    private final TradeRetriever tradeRetreiver;
    private final BuyingStrategy buyingStrategy;
    private final SellingStrategy sellingStrategy;

    public SingleTradeRunner(Symbol symbol, Set<TradeEvaluator> tradeEvaluators, TradeRetrieverFactory tradeRetrieverFactory, PortfolioManager profileManager, BuyingStrategy buyingStrategy,
            SellingStrategy sellingStrategy) {
        this.symbol = symbol;
        this.evaluators = tradeEvaluators;
        this.tradeRetreiver = tradeRetrieverFactory.getTradeRetriever(symbol);
        this.portfolioManager = profileManager;
        this.buyingStrategy = buyingStrategy;
        this.sellingStrategy = sellingStrategy;
    }

    public void run(Date time) {
        Trade trade = tradeRetreiver.retrieve(time);
        TradingContext context = new TradingContext();
        context.setPortfolio(portfolioManager.getPortfolio());
        context.setCurrentTrade(trade);
        context.setLast30Trades(tradeRetreiver.lastNumOfTrades(time, 30));

        // calculate aggregated buy/sell score from evaluators
        float buyScoreSum = 0, sellScoreSum = 0;
        TradeEvaluation evaluation, finalEvaluation;
        for (TradeEvaluator evaluator : evaluators) {
            evaluation = evaluator.evaluate(context);
            buyScoreSum += evaluation.getBuyScore();
            sellScoreSum += evaluation.getSellScore();
        }
        finalEvaluation = new TradeEvaluation(buyScoreSum / evaluators.size(), sellScoreSum / evaluators.size());

        BuyOrder buyOrder = buyingStrategy.shouldBuy(finalEvaluation, context);
        if (buyOrder != null) {
        	log.info("Buy order created: {}", buyOrder);
            portfolioManager.executeOrder(buyOrder);
        } else {
        	log.debug("No buy order created on evaluation {}", finalEvaluation);
        }
        SellOrder sellOrder = sellingStrategy.shouldSell(finalEvaluation, context);
        if (sellOrder != null) {
        	log.info("Sell order created: {}", buyOrder);
            portfolioManager.executeOrder(sellOrder);
        } else {
        	log.debug("No sell order created on evaluation {}", finalEvaluation);
        }

    }
}
