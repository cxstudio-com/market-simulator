package com.cxstudio.trading.simulator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cxstudio.trading.PortfolioManager;
import com.cxstudio.trading.SimulatedExecutor;
import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.evaluator.MovingAverageEvaluator;
import com.cxstudio.trading.evaluator.SupportResistenceEvaluator;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.persistence.db.SymbolDbDao;
import com.cxstudio.trading.strategy.SimpleConfidenceBuyStrategy;
import com.cxstudio.trading.strategy.SimpleConfidenceSellStrategy;

@Configuration
@ComponentScan("com.cxstudio.trading")
public class SimulationConfigure {
    @Autowired
    private SymbolDbDao symbolDao;

    @Value("${simulator.initialCash:100000}")
    private float initialCash;

    @Bean
    PortfolioManager portfolioManager(SimulatedExecutor executor) {
        PortfolioManager pm = new PortfolioManager(executor);
        pm.setPortfolio(new Portfolio(initialCash));
        return pm;
    }

    @Bean
    public Simulation simulation(MovingAverageEvaluator movingAverageEval, SupportResistenceEvaluator supResistEval, PortfolioManager portfolioManager, SimpleConfidenceBuyStrategy buyingStrategy,
            SimpleConfidenceSellStrategy sellingStrategy) {
        Set<TradeEvaluator> evaluators = new HashSet<TradeEvaluator>(2);
        evaluators.add(movingAverageEval);
        evaluators.add(supResistEval);
        return new Simulation(symbolDao, evaluators, portfolioManager, buyingStrategy, sellingStrategy);
    }

}
