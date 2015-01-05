package com.cxstudio.trading.simulator;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.cxstudio.trading.PortfolioManager;
import com.cxstudio.trading.SimulatedExecutor;
import com.cxstudio.trading.TradeEvaluator;
import com.cxstudio.trading.evaluator.MovingAverageEvaluator;
import com.cxstudio.trading.evaluator.SupportResistenceEvaluator;
import com.cxstudio.trading.model.Portfolio;
import com.cxstudio.trading.strategy.RandomBuyStrategy;
import com.cxstudio.trading.strategy.RandomSellStrategy;

@Configuration
@ComponentScan(basePackages = {"com.cxstudio.trading"})
@ImportResource("classpath:persistent-context.xml")
public class SimulationConfigure {
    static Logger log = LoggerFactory.getLogger(SimulationConfigure.class);

    @Value("${simulator.initialCash:100000}")
    private float initialCash;

    @Bean
    public PortfolioManager portfolioManager(SimulatedExecutor executor) {
        PortfolioManager pm = new PortfolioManager(executor);
        pm.setPortfolio(new Portfolio(initialCash));
        return pm;
    }

    @Bean
    public Simulation simulation(MovingAverageEvaluator movingAverageEval, SupportResistenceEvaluator supResistEval, PortfolioManager portfolioManager, RandomBuyStrategy buyingStrategy,
            RandomSellStrategy sellingStrategy) {
        Set<TradeEvaluator> evaluators = new HashSet<TradeEvaluator>(2);
        evaluators.add(movingAverageEval);
        evaluators.add(supResistEval);
        log.debug("portfolioManager.getProtfolio {}", portfolioManager.getPortfolio());
        return new Simulation(evaluators, portfolioManager, buyingStrategy, sellingStrategy);
    }

}
