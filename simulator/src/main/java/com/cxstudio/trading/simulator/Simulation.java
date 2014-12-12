package com.cxstudio.trading.simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cxstudio.trading.PortfolioManager;
import com.cxstudio.trading.SimulatedTradeRetrieverFactory;
import com.cxstudio.trading.SingleTradeRunner;
import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.dao.SymbolDao;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.strategy.BuyingStrategy;
import com.cxstudio.trading.strategy.SellingStrategy;

public class Simulation {
    private List<Symbol> symbols;
    private final SymbolDao symbolDao;
    private final Set<TradeEvaluator> evaluators;
    private final PortfolioManager portfolioManager;
    private final BuyingStrategy buyingStrategy;
    private final SellingStrategy sellingStrategy;
    private final Date startTime;
    private static SimulatedTradeRetrieverFactory retrieverFactory;

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimulationConfigure.class);
        retrieverFactory = (SimulatedTradeRetrieverFactory) ctx.getBean("simulatedTradeRetrieverFactory");
        Simulation simulation = (Simulation) ctx.getBean("simulation");
        simulation.run();
    }

    public Simulation(SymbolDao symbolDao, Set<TradeEvaluator> evaluators, PortfolioManager portfolioManager, BuyingStrategy buyingStrategy, SellingStrategy sellingStrategy) {
        this.symbolDao = symbolDao;
        this.evaluators = evaluators;
        this.portfolioManager = portfolioManager;
        this.buyingStrategy = buyingStrategy;
        this.sellingStrategy = sellingStrategy;
        this.startTime = new Date();
    }

    private void run() {
        symbols = symbolDao.getAllSymbols(true);
        List<SingleTradeRunner> runners = new ArrayList<SingleTradeRunner>();
        for (Symbol symbol : symbols) {
            SingleTradeRunner runner = new SingleTradeRunner(symbol, evaluators, retrieverFactory, portfolioManager, buyingStrategy, sellingStrategy);
            runners.add(runner);
        }
        BatchTradeRunner batchRunner = new BatchTradeRunner(runners);
        AcceleratedScheduler scheduler = new AcceleratedScheduler(batchRunner, startTime, 60, 5);
        scheduler.start();
    }
}
