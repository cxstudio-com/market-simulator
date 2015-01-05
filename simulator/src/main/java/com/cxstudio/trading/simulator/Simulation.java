package com.cxstudio.trading.simulator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cxstudio.trading.PortfolioManager;
import com.cxstudio.trading.SimulatedTradeRetrieverFactory;
import com.cxstudio.trading.SingleTradeRunner;
import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.dao.TradeDao;
import com.cxstudio.trading.model.Symbol;
import com.cxstudio.trading.model.TradeSpacing;
import com.cxstudio.trading.persistence.db.SymbolDbDao;
import com.cxstudio.trading.persistence.db.TradeDbDao;
import com.cxstudio.trading.strategy.BuyingStrategy;
import com.cxstudio.trading.strategy.SellingStrategy;

public class Simulation {
    private List<Symbol> symbols;
    private final Set<TradeEvaluator> evaluators;
    private final PortfolioManager portfolioManager;
    private final BuyingStrategy buyingStrategy;
    private final SellingStrategy sellingStrategy;
    private Date startTime;
    private static SimulatedTradeRetrieverFactory retrieverFactory;
    static Logger log = LoggerFactory.getLogger(Simulation.class);

    @Autowired
    private SymbolDbDao symbolDao;

    @Autowired
    private TradeDbDao tradeDao;

    public static void main(String[] args) throws ParseException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimulationConfigure.class);
        TradeDao tradeDao = (TradeDao) ctx.getBean("tradeDbDao");
        retrieverFactory = new SimulatedTradeRetrieverFactory(tradeDao, TradeSpacing.MINUTE, 480);
        Simulation simulation = (Simulation) ctx.getBean("simulation");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        simulation.setStartTime(dateFormat.parse("03/02/2009"));
        simulation.run();
    }

    public Simulation(Set<TradeEvaluator> evaluators, PortfolioManager portfolioManager, BuyingStrategy buyingStrategy, SellingStrategy sellingStrategy) {
        this.evaluators = evaluators;
        this.portfolioManager = portfolioManager;
        this.buyingStrategy = buyingStrategy;
        this.sellingStrategy = sellingStrategy;
    }

    private void run() {
        // symbols = symbolDao.getAllSymbols(true);
        symbols = new ArrayList<Symbol>();
        symbols.add(symbolDao.getSymbol("AAPL"));

        List<SingleTradeRunner> runners = new ArrayList<SingleTradeRunner>();
        for (Symbol symbol : symbols) {
            SingleTradeRunner runner = new SingleTradeRunner(symbol, evaluators, retrieverFactory, portfolioManager, buyingStrategy, sellingStrategy);
            runners.add(runner);
        }
        BatchTradeRunner batchRunner = new BatchTradeRunner(runners);
        AcceleratedScheduler scheduler = new AcceleratedScheduler(batchRunner, startTime, 60, 1, tradeDao);
        scheduler.start();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

}
