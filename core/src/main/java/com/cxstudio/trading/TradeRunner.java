package com.cxstudio.trading;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cxstudio.trading.model.BuyOrder;
import com.cxstudio.trading.model.Position;
import com.cxstudio.trading.model.SellOrder;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;
import com.cxstudio.trading.strategy.BuyingStrategy;
import com.cxstudio.trading.strategy.SellingStrategy;

public class TradeRunner {
    private final Set<TradeEvaluator> evaluators;
    private final Symbol symbol;
    private List<Position> openPositions;
    private final TradeRetriever tradeRetreiver;
    private final OrderExecutor orderExecutor;
    private final BuyingStrategy buyingStrategy;
    private final SellingStrategy sellingStrategy;

    public TradeRunner(Symbol symbol, Set<TradeEvaluator> tradeEvaluators, TradeRetriever tradeRetriever, OrderExecutor executor, BuyingStrategy buyingStrategy, SellingStrategy sellingStrategy) {
        this.symbol = symbol;
        this.evaluators = tradeEvaluators;
        this.tradeRetreiver = tradeRetriever;
        openPositions = new ArrayList<Position>();
        this.orderExecutor = executor;
        this.buyingStrategy = buyingStrategy;
        this.sellingStrategy = sellingStrategy;
    }

    public void run(Date time) {
        Trade trade = tradeRetreiver.retrieve(time);
        TradingContext context = new TradingContext();
        context.setOpenPositions(openPositions);
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
            orderExecutor.executeOrder(buyOrder);
        }
        SellOrder sellOrder = sellingStrategy.shouldSell(finalEvaluation, context);
        if (sellOrder != null) {
            orderExecutor.executeOrder(sellOrder);
        }

    }
}
