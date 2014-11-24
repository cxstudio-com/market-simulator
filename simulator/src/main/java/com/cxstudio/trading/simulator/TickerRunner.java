package com.cxstudio.trading.simulator;

import com.cxstudio.trading.model.Symbol;

public class TickerRunner implements Runnable {
    private final Symbol symbol;

    public TickerRunner(Symbol symbol) {
        this.symbol = symbol;
    }

    public void run() {

    }
}
