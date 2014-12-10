package com.cxstudio.trading;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cxstudio.trading.model.OpenPosition;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.Trade;
import com.cxstudio.trading.model.TradeEvaluation;

public class TradeRunner {
    private final Set<TradeEvaluator> evaluators;
    private final Symbol symbol;
    private List<OpenPosition> openPositions;
    private final TradeRetriever tradeRetreiver;

    public TradeRunner(Symbol symbol, Set<TradeEvaluator> tradeEvaluators, TradeRetriever tradeRetriever) {
        this.symbol = symbol;
        this.evaluators = tradeEvaluators;
        this.tradeRetreiver = tradeRetriever;
        openPositions = new ArrayList<OpenPosition>();
    }

    public void run(Date time) {
        Trade trade = tradeRetreiver.retrieve(time);
        TradingContext context = new TradingContext();
        context.setCunrrentTrade(trade);
        context.setLast30Trades(tradeRetreiver.lastNumOfTrades(time, 30));

        float buyScoreSum = 0, sellScoreSum = 0;
        TradeEvaluation evaluation, finalEvaluation;
        for (TradeEvaluator evaluator : evaluators) {
            evaluation = evaluator.evaluate(context);
            buyScoreSum += evaluation.getBuyScore();
            sellScoreSum += evaluation.getSellScore();
        }
        finalEvaluation = new TradeEvaluation(buyScoreSum / evaluators.size(), sellScoreSum / evaluators.size());
        if (finalEvaluation.getBuyScore() > finalEvaluation.getSellScore()) {
            // buy
        } else if (finalEvaluation.getBuyScore() < finalEvaluation.getSellScore()) {
            // sell
        } // if equal no action

    }
}
