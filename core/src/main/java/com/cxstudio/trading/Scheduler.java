package com.cxstudio.trading;

public interface Scheduler<T extends TradeRunner> {

    public void start(T tradeRunner);

    public void stop();
}
