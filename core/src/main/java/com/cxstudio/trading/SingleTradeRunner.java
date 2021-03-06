package com.cxstudio.trading;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    private static int monthOfYear = -1;

    public SingleTradeRunner(Symbol symbol, Set<TradeEvaluator> tradeEvaluators,
            TradeRetrieverFactory tradeRetrieverFactory, PortfolioManager profileManager,
            BuyingStrategy buyingStrategy, SellingStrategy sellingStrategy) {
        this.symbol = symbol;
        this.evaluators = tradeEvaluators;
        this.tradeRetreiver = tradeRetrieverFactory.getTradeRetriever(symbol);
        this.portfolioManager = profileManager;
        this.buyingStrategy = buyingStrategy;
        this.sellingStrategy = sellingStrategy;
    }

    public void run(Date time) {
        Trade trade = tradeRetreiver.retrieve(time);
        if (trade == null) {
            return; // if no trades found for given time, nothing to do
        }
        TradingContext context = new TradingContext();
        context.setPortfolio(portfolioManager.getPortfolio());
        context.setCurrentTrade(trade);
        context.setLast30Trades(tradeRetreiver.lastNumOfTrades(time, 30));
        portfolioManager.updateLatestQuote(trade);

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
            try {
                portfolioManager.executeOrder(buyOrder);
            } catch (Exception e) {
                log.error("Unable to execute a buy order: " + e.getMessage());
            }
        } else {
            log.debug("No buy order created on evaluation {}", finalEvaluation);
        }
        SellOrder sellOrder = sellingStrategy.shouldSell(finalEvaluation, context);
        if (sellOrder != null) {
            log.info("Sell order created: {}", sellOrder);
            try {
                portfolioManager.executeOrder(sellOrder);
            } catch (Exception e) {
                log.error("Unable to execute a sell order.", e);
            }
        } else {
            log.debug("No sell order created on evaluation {}", finalEvaluation);
        }

        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(time);
        NumberFormat numFormat = NumberFormat.getCurrencyInstance();

        if (currentCal.get(Calendar.MONTH) != monthOfYear) {
            log.info("Running single task for the month of {}, {} {} total portolio worth >>>{}<<<",
                    currentCal.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.getDefault()),
                    currentCal.get(Calendar.YEAR), portfolioManager.getPortfolio(),
                    numFormat.format(portfolioManager.getCurrentPortfolioWorth()));
            monthOfYear = currentCal.get(Calendar.MONTH);
        }
        log.debug("Single run completed. Portfolio: {}", portfolioManager.getPortfolio());

    }
}
