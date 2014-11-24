package com.cxstudio.trading;

import java.util.ArrayList;
import java.util.List;

import com.cxstudio.trading.model.OpenPosition;
import com.cxstudio.trading.model.Symbol;

public class TradeRunner {
    private final TradeEvaluator tradeEvaluator;
    private final Symbol symbol;
    private List<OpenPosition> openPositions;
    private final TradeRetriever tradeRetreiver;

    public TradeRunner(Symbol symbol, TradeEvaluator tradeEvaluator) {
        this.symbol = symbol;
        this.tradeEvaluator = tradeEvaluator;
        openPositions = new ArrayList<OpenPosition>();
    }

    public void run(Date time) {
        // TODO Auto-generated method stub

    }
}
