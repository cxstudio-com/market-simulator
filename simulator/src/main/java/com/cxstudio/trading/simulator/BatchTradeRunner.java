package com.cxstudio.trading.simulator;

import java.util.Date;
import java.util.List;

import com.cxstudio.trading.SingleTradeRunner;
import com.cxstudio.trading.TradeRunner;

public class BatchTradeRunner implements TradeRunner {
    private List<SingleTradeRunner> tradeRunners;

    public BatchTradeRunner(List<SingleTradeRunner> tradeRunners) {
        this.tradeRunners = tradeRunners;
    }

    public void run(Date time) {
        for (SingleTradeRunner tradeRunner : tradeRunners) {
            tradeRunner.run(time);
        }
    }
}
